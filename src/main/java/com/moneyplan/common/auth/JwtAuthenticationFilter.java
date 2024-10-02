package com.moneyplan.common.auth;


import static com.moneyplan.common.auth.model.SecurityConstants.PERMITTED_URI;

import com.moneyplan.common.auth.model.JwtRule;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        if (isPermittedURI(request.getRequestURI())) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);
        if (jwtService.validateAccessToken(accessToken)) {
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);
        Member member = findMemberByRefreshToken(refreshToken);

        if (jwtService.validateRefreshToken(refreshToken, member.getId())) {

            String reissuedAccessToken = jwtService.generateAccessToken(response, member);
            jwtService.generateRefreshToken(response, member);

            setAuthenticationToContext(reissuedAccessToken);
            filterChain.doFilter(request, response);
            return;
        }

        jwtService.logout(member, response);
    }

    private boolean isPermittedURI(String requestURI) {
        return Arrays.stream(PERMITTED_URI)
            .anyMatch(permitted -> {
                String replace = permitted.replace("*", "");
                return requestURI.contains(replace) || replace.contains(requestURI);
            });
    }

    private Member findMemberByRefreshToken(String refreshToken) {
        String id = jwtService.getIdFromRefresh(refreshToken);
        return memberRepository.findById(Long.valueOf(id))
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void setAuthenticationToContext(String token) {
        Authentication authentication = jwtService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
