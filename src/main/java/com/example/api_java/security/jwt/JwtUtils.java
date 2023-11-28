package com.example.api_java.security.jwt;

import com.example.api_java.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        //lấy thông tin người dùng trả về chuỗi token được mã hóa

        //lấy thông tin người dùng authentication.getPrincipal();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        //mã hóa HS512
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //  nhận một chuỗi JWT và trả về tên người dùng được từ giải mã trong JWT.
    public String getUserNameFromJwtToken(String token) {
        //Jwts.parser() tạo một đối tượng xử lý JWS JSON Web Signature -> giải mã token
        //setSigningKey(jwtSecret) xác định khóa bí mật được sử dụng để xác thực JWT
        //parseClaimsJws(token) giải mã chuỗi JWT về đối tượng Jws<Claims> chứa các thông tin trong JWT
        //getBody().getSubject() trả về tên người dùng được lấy từ phần thân (body) của JWT
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    //xác thực token JWT được cung cấp bởi người dùng.
    // Nó trả về true nếu token hợp lệ và false nếu không hợp lệ.
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
