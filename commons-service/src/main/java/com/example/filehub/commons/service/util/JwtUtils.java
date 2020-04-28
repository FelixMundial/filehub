//package com.example.filehub.commons.service.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.*;
//
///**
// * @deprecated
// * @author yinfelix
// * @date 2020/3/8
// */
//@Slf4j
//public class JwtUtils {
//    public static final String CLAIM_KEY_SUBJECT = "user";
//    public static final String CLAIM_KEY_CREATED = "created";
//
//    public static String getSubjectFromToken(String token, PublicKey publicKey) {
//        String subject;
//        try {
//            final Claims claims = getClaimsFromToken(token, publicKey);
//            subject = (String) claims.get(CLAIM_KEY_SUBJECT);
//        } catch (Exception e) {
//            log.error("Cannot get subject from this token: {e}", e);
//            subject = null;
//        }
//        return subject;
//    }
//
//    public static Date getCreationDateFromToken(String token, PublicKey publicKey) {
//        Date created;
//        try {
//            final Claims claims = getClaimsFromToken(token, publicKey);
//            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
//        } catch (Exception e) {
//            log.error("Cannot get creation date from this token: {e}", e);
//            created = null;
//        }
//        return created;
//    }
//
//    public static Date getExpirationDateFromToken(String token, PublicKey publicKey) {
//        Date expiration;
//        try {
//            final Claims claims = getClaimsFromToken(token, publicKey);
//            expiration = claims.getExpiration();
//        } catch (Exception e) {
//            log.error("Cannot get expiration date from this token: {e}", e);
//            expiration = null;
//        }
//        return expiration;
//    }
//
//    private static Claims getClaimsFromToken(String token, PublicKey publicKey) {
//        Claims claims;
//        /*
//        若token内容或格式错误
//         */
//        try {
//            claims = Jwts.parserBuilder()
//                    .setSigningKey(publicKey).build()
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            log.error("Cannot get claims from this token: {e}", e);
//            claims = null;
//        }
//        return claims;
//    }
//
//    private static Date generateExpirationDate(int ttlInMinutes) {
//        return new Date(System.currentTimeMillis() + ttlInMinutes * 6000);
//    }
//
//    private static Boolean isTokenExpired(String token, PublicKey publicKey) {
//        final Date expiration = getExpirationDateFromToken(token, publicKey);
//        return expiration.before(new Date());
//    }
//
//    /**
//     * 只将UserDetails用户名放入token
//     * @param userDetails
//     * @param privateKey
//     * @param ttlInMinutes
//     * @return
//     */
//    public static String generateToken(UserDetails userDetails, PrivateKey privateKey, int ttlInMinutes) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(CLAIM_KEY_SUBJECT, userDetails.getUsername());
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims, privateKey, ttlInMinutes);
//    }
//
//    private static String generateToken(Map<String, Object> claims, PrivateKey privateKey, int ttlInMinutes) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(generateExpirationDate(ttlInMinutes))
//                .setId(createJti())
//                .signWith(privateKey, SignatureAlgorithm.RS256)
//                .compact();
//    }
//
//    public static Boolean canTokenBeRefreshed(String token, PublicKey publicKey) {
//        return !isTokenExpired(token, publicKey);
//    }
//
//    public static String refreshToken(String token, PublicKey publicKey, PrivateKey privateKey, int ttlInMinutes) {
//        String refreshedToken;
//        try {
//            final Claims claims = getClaimsFromToken(token, publicKey);
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            refreshedToken = generateToken(claims, privateKey, ttlInMinutes);
//        } catch (Exception e) {
//            log.error("This token cannot be refreshed: {e}", e);
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }
//
//    public static Boolean validateToken(String token, UserDetails userDetails, PublicKey publicKey) {
//        User user = (User) userDetails;
//        final String username = getSubjectFromToken(token, publicKey);
//        return username.equals(user.getUsername()) && !isTokenExpired(token, publicKey);
//    }
//
//    private static String createJti() {
//        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
//    }
//
//    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//        return (lastPasswordReset != null && created.before(lastPasswordReset));
//    }
//}
