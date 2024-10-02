package com.moneyplan.common.auth;

import com.moneyplan.common.auth.model.JwtRule;
import com.moneyplan.common.auth.model.TokenStatus;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.common.util.JwtGenerator;
import com.moneyplan.common.util.JwtUtil;
import com.moneyplan.member.domain.Member;
import com.moneyplan.refresh_token.domain.RefreshToken;
import com.moneyplan.refresh_token.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class JwtService {

    private final CustomUserDetailService customUserDetailService;
    private final JwtGenerator jwtGenerator;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final Key ACCESS_SECRET_KEY;
    private final Key REFRESH_SECRET_KEY;
    private final long ACCESS_EXPIRATION;
    private final long REFRESH_EXPIRATION;

    public JwtService(CustomUserDetailService customUserDetailService, JwtGenerator jwtGenerator,
        JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository,
        @Value("${jwt.key.access}") String ACCESS_SECRET_KEY,
        @Value("${jwt.key.refresh}") String REFRESH_SECRET_KEY,
        @Value("${jwt.time.access}") long ACCESS_EXPIRATION,
        @Value("${jwt.time.refresh}") long REFRESH_EXPIRATION
    ) {
        this.customUserDetailService = customUserDetailService;
        this.jwtGenerator = jwtGenerator;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.ACCESS_SECRET_KEY = jwtUtil.getSigningKey(ACCESS_SECRET_KEY);
        this.REFRESH_SECRET_KEY = jwtUtil.getSigningKey(REFRESH_SECRET_KEY);
        this.ACCESS_EXPIRATION = ACCESS_EXPIRATION;
        this.REFRESH_EXPIRATION = REFRESH_EXPIRATION;
    }

    public String generateAccessToken(HttpServletResponse response, Member requestMember) {
        String accessToken = jwtGenerator.generateAccessToken(ACCESS_SECRET_KEY, ACCESS_EXPIRATION,
            requestMember);
        ResponseCookie cookie = setTokenToCookie(JwtRule.ACCESS_PREFIX.getValue(), accessToken,
            ACCESS_EXPIRATION / 1000);
        response.addHeader(JwtRule.JWT_ISSUE_HEADER.getValue(), cookie.toString());

        return accessToken;
    }

    @Transactional
    public String generateRefreshToken(HttpServletResponse response, Member requestMember) {
        String refreshToken = jwtGenerator.generateRefreshToken(REFRESH_SECRET_KEY,
            REFRESH_EXPIRATION, requestMember);
        ResponseCookie cookie = setTokenToCookie(JwtRule.REFRESH_PREFIX.getValue(), refreshToken,
            REFRESH_EXPIRATION / 1000);
        response.addHeader(JwtRule.JWT_ISSUE_HEADER.getValue(), cookie.toString());

        refreshTokenRepository.save(
            RefreshToken.builder()
                .member(requestMember)
                .token(refreshToken)
                .build());

        return refreshToken;
    }

    /*
     * Cookie에 토큰 저장
     */
    private ResponseCookie setTokenToCookie(String tokenPrefix, String token, long maxAgeSeconds) {
        return ResponseCookie.from(tokenPrefix, token)
            .path("/")
            .maxAge(maxAgeSeconds)
            .httpOnly(true)
            .sameSite("Lax")
            .secure(false)
            .build();
    }

    /*
     * Cookie에서 원하는 토큰 추출
     */
    public String resolveTokenFromCookie(HttpServletRequest request, JwtRule tokenPrefix) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new BusinessException(ErrorCode.MISSING_TOKEN_IN_COOKIE);
        }
        return jwtUtil.resolveTokenFromCookie(cookies, tokenPrefix);
    }

    public boolean validateAccessToken(String token) {
        return jwtUtil.getTokenStatus(token, ACCESS_SECRET_KEY) == TokenStatus.AUTHENTICATED;
    }

    public boolean validateRefreshToken(String token, Long memberId) {
        boolean isRefreshValid = jwtUtil.getTokenStatus(token, REFRESH_SECRET_KEY) == TokenStatus.AUTHENTICATED;

        RefreshToken storedToken = refreshTokenRepository.findByMember_Id(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.JWT_TOKEN_NOT_FOUND));
        boolean isTokenMatched = storedToken.getToken().equals(token);

        return isRefreshValid && isTokenMatched;
    }

    public Authentication getAuthentication(String token) {
        UserDetails principal = customUserDetailService.loadUserByUsername(getMemberPk(token, ACCESS_SECRET_KEY));
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    private String getMemberPk(String token, Key secretKey) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public String getIdFromRefresh(String refreshToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(REFRESH_SECRET_KEY)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getSubject();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    public void logout(Member requestMember, HttpServletResponse response) {
        refreshTokenRepository.deleteById(requestMember.getId());

        Cookie accessCookie = jwtUtil.resetToken(JwtRule.ACCESS_PREFIX);
        Cookie refreshCookie = jwtUtil.resetToken(JwtRule.REFRESH_PREFIX);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

}
