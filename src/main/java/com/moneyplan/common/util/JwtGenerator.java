package com.moneyplan.common.util;

import com.moneyplan.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtGenerator {

    public String generateAccessToken(final Key ACCESS_SECRET, final long ACCESS_EXPIRATION, Member member) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
            .setHeader(createHeader())
            .setClaims(createClaims(member))
            .setSubject(String.valueOf(member.getId()))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + ACCESS_EXPIRATION))
            .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(final Key REFRESH_SECRET, final long REFRESH_EXPIRATION, Member member) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
            .setHeader(createHeader())
            .setSubject(String.valueOf(member.getId()))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + REFRESH_EXPIRATION))
            .signWith(REFRESH_SECRET, SignatureAlgorithm.HS256)
            .compact();
    }

    private Map<String, Object> createHeader() {
        Header header = Jwts.header();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(Member member) {
        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("account", member.getAccount());
        return claims;
    }

}
