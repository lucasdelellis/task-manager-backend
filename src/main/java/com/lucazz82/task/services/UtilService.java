package com.lucazz82.task.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lucazz82.task.handlers.InvalidTokenException;

@Service
public class UtilService {
	private static int expireTime = 10 * 60 * 1000;
	private static Algorithm algorithm = Algorithm.HMAC256("secret key".getBytes());
	
	public static DecodedJWT getJWTFromHeader(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {		
			throw new InvalidTokenException("bearer token missing", 404);
		}
		try {
			String token = authorizationHeader.substring("Bearer ".length());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			
			return decodedJWT;
		} catch (TokenExpiredException e) {
			throw new InvalidTokenException("token has expired", 404);
		} catch (Exception e) {
			throw new InvalidTokenException("invalid token", 404);
		}
	}
	
	public static String getAccessToken(String username, List<String> authorities, String url) {
		return JWT.create().withSubject(username)
		.withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
		.withIssuer(url).withClaim("roles", authorities).sign(algorithm);
	}
	
	public static String getRefreshToken(String username, String url) {
		return JWT.create().withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + 3 * expireTime))
				.withIssuer(url).sign(algorithm);
	}
}
