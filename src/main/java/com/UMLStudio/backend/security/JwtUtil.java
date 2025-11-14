package com.UMLStudio.backend.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtUtil implements JwtServicePort {

    @Value("${jwt.secret:changeitchangethissecretkeythatshouldbelonger}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;

    private SecretKeySpec secretKeySpec;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    @Override
    public String generateToken(String username, Long userId) {
        try {
            long now = Instant.now().toEpochMilli();
            long exp = now + jwtExpirationMs;

            Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
            Map<String, Object> payload = Map.of(
                    "sub", username,
                    "userId", userId,
                    "iat", now,
                    "exp", exp
            );

            String headerJson = objectMapper.writeValueAsString(header);
            String payloadJson = objectMapper.writeValueAsString(payload);

            String headerBase = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
            String payloadBase = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));
            String signingInput = headerBase + "." + payloadBase;
            String signature = base64UrlEncode(hmacSha256(signingInput.getBytes(StandardCharsets.UTF_8)));

            return signingInput + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;
            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            String signingInput = header + "." + payload;
            String expectedSig = base64UrlEncode(hmacSha256(signingInput.getBytes(StandardCharsets.UTF_8)));
            if (!constantTimeEquals(expectedSig, signature)) return false;

            // parse payload and check exp
            String payloadJson = new String(base64UrlDecode(payload), StandardCharsets.UTF_8);
            Map<?, ?> claims = objectMapper.readValue(payloadJson, Map.class);
            Object expObj = claims.get("exp");
            if (expObj == null) return false;
            long exp = ((Number) expObj).longValue();
            long now = Instant.now().toEpochMilli();
            return now < exp;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            Map<?, ?> claims = getClaims(token);
            Object sub = claims.get("sub");
            return sub != null ? sub.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getUserIdFromToken(String token) {
        try {
            Map<?, ?> claims = getClaims(token);
            Object id = claims.get("userId");
            if (id instanceof Number) return ((Number) id).longValue();
            if (id instanceof String) return Long.parseLong((String) id);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<?, ?> getClaims(String token) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid token format");
        String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
        return objectMapper.readValue(payloadJson, Map.class);
    }

    private byte[] hmacSha256(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(data);
    }

    private static String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static byte[] base64UrlDecode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }

    private static boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
