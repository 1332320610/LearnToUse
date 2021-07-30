package icu.hututublog.jjwttest.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JJWTUtil {


    public static final String APP_ID = "1.0";
    public static final String APP_SECRET = "90ag0dhj0gfj987sa76dfg6d6h687jgsj586gfj8sadfg9ad";
    public static final long DAY_TTL = 86400000;


    /**
     * Simple method to construct a JWT.
     *
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token.
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret.
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(APP_ID + APP_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims.
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }

        return jwtBuilder.compact();
    }

    /**
     * Simple method to validate and read the JWT.
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS(as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(APP_ID + APP_SECRET))
                .parseClaimsJws(jwt).getBody();

        return claims;
    }

}
