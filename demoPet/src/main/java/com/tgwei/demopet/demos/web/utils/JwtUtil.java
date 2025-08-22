package com.tgwei.demopet.demos.web.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 提供JWT Token的生成、解析和验证功能
 */
@Component
public class JwtUtil {

    // 使用Keys.secretKeyFor生成安全的密钥
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成JWT Token
     * @param claims 自定义声明信息
     * @param username 用户名
     * @return 生成的JWT Token字符串
     */
    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 从Token中获取用户名
     * @param token JWT Token字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 验证Token是否过期
     * @param token JWT Token字符串
     * @return true表示已过期，false表示未过期
     */
    public Boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 从Token中获取Claims信息
     * @param token JWT Token字符串
     * @return Claims对象，包含Token中的所有声明信息
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

