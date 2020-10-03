package com.ascending.service;

import com.ascending.model.Role;
import com.ascending.model.User;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Set;
@Service
public class JWTService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String SECRET_KEY = System.getProperty("secret.key");
    private final String ISSUER = "com.ascending";
    private final long EXPIRATION_TIME = 3600*1000;
    /*1. decide signature algorithm type
    * 2. hard code secret key -- later use VM option to pass in the key
    * 3. sign JWT with security key
    * 4. organize our payload: Claims ->>map claims have some predefined keys, add your own customer keys and value pair
    * 5. set claims JWT api
    * 6. generate the token
    */
    public String generateToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(user.getId()));
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setSubject(user.getName());
        claims.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME));

        Set<Role> roles = user.getRoles();
        String allowedReadResources = "";
        String allowedCreateResources = "";
        String allowedUpdateResources = "";
        String allowedDeleteResources = "";

        for (Role role:roles){
            if(role.isIfAllowedRead())
                allowedReadResources = String.join(role.getAllowedResource(),allowedReadResources,",");
            if(role.isIfAllowedCreate())
                allowedCreateResources = String.join(role.getAllowedResource(),allowedCreateResources,",");
            if(role.isIfAllowedUpdate())
                allowedUpdateResources = String.join(role.getAllowedResource(),allowedUpdateResources,",");
            if (role.isIfAllowedDelete())
                allowedDeleteResources = String.join(role.getAllowedResource(),allowedDeleteResources,",");
        }



        claims.put("allowedReadResources",allowedReadResources.replaceAll(",$",""));
        claims.put("allowedCreateResources",allowedCreateResources.replaceAll(",$",""));
        claims.put("allowedUpdateResources",allowedUpdateResources.replaceAll(",$",""));
        claims.put("allowedDeleteResources",allowedDeleteResources.replaceAll(",$",""));

        logger.info("=====,allowedReadResources = {}",allowedReadResources);
        logger.info("=====,allowedCreateResources = {}",allowedCreateResources);
        logger.info("=====,allowedUpdateResources = {}",allowedUpdateResources);
        logger.info("=====,allowedDeleteResources = {}",claims.get("allowedDeleteResources"));

        //Builds the JWT and serialized it to a compact, URL-SAFE string
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm,signingKey);
        String generatedToken = builder.compact();
        return generatedToken;
    }
    public Claims decryptJwtToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public boolean hasTokenExpired(String token){
        Date expireDate = decryptJwtToken(token).getExpiration();
        Date currentDate = new Date (System.currentTimeMillis());
        return expireDate.before(currentDate);
    }
}
