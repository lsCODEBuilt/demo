/*
 * Copyright (c) 2017-2018 http://ycode.cn
 */
package com.test.common.untils;


import com.google.common.collect.Maps;
import com.test.common.constants.Constants;
import com.test.common.exception.InvalidTokenException;
import com.test.common.security.Session;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * cloudx.auth.utils
 *
 * @author Elve.xu [xuhongwei@ycode.cn]
 * @version v1.0 - 02/01/2018.
 */
@Profile("dev")
@Slf4j
public final class JwtUtils {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * encrypt token with some parameter
     *
     * @param subject subject
     * @param expire expire date
     * @param claims params
     * @return token
     */
    public static String encryptToken(String subject, Date expire, Map<String, Object> claims) {

        JwtBuilder builder = Jwts.builder().setSubject(subject);

        if (claims != null) {
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }
        }
        return builder.setExpiration(expire).signWith(SignatureAlgorithm.HS256, SecretKeyConfiguration.secretKey)
                .compact();
    }

    public static String encryptToken(String subject, Map<String, Object> claims) {

        JwtBuilder builder = Jwts.builder().setSubject(subject);

        if (claims != null) {
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }
        }
        return builder.signWith(SignatureAlgorithm.HS256, SecretKeyConfiguration.secretKey)
                .compact();
    }

    /**
     * decrypt jwt token
     *
     * @param token token value
     * @return return claim map
     */
    public static Map<String, Object> decryptToken(String token) throws ExpiredJwtException,InvalidTokenException {
        Map<String, Object> result = Maps.newHashMap();

        try {
            final Claims claims = Jwts.parser().setSigningKey(SecretKeyConfiguration.secretKey).parseClaimsJws(token)
                    .getBody();
            Set<String> keys = claims.keySet();
            for (String key : keys) {
                result.put(key, claims.get(key));
            }
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Throwable ignored) {
            throw new InvalidTokenException(ignored);
        }
        return result;
    }

    /**
     * decrypt jwt token
     *
     * @param token token value
     * @return return claim map
     */
    public static Session decryptTokenToSession(String token) {
        Session session = null;

        try {
            final Claims claims = Jwts.parser().setSigningKey(SecretKeyConfiguration.secretKey).parseClaimsJws(token)
                .getBody();
            Set<String> keys = claims.keySet();
            session = new Session();
            for (String key : keys) {
                if(Constants.TOKEN_CLAIMS_KEY_UID.equals(key)){
                    session.setUserId(String.valueOf(claims.get(key)));
                }else if(Constants.TOKEN_CLAIMS_KEY_MOBILE.equals(key)){
                    session.setLoginName((String)claims.get(key));
                }else if(Constants.TOKEN_CLAIMS_KEY_CLIENT_TYPE.equals(key)){
                    session.setClientType((Integer)claims.get(key));
                }
            }
        } catch (ExpiredJwtException e) {
            throw e;
        }catch (Exception e) {
            log.error("decrypt token {} error {}",token,e);
        }
        return session;
    }


    @Configuration
    public static class SecretKeyConfiguration {

        private static String secretKey;

        @Value("251f557cdcd3fdb8acc73893d58962a3f21abdfb")
        public void setSecretKey(String secretKey) {
            SecretKeyConfiguration.secretKey = secretKey;
        }
    }

}
