package com.lucazz82.task.services;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lucazz82.task.handlers.InvalidTokenException;

@Service
public class UtilService {
	
	public static DecodedJWT getJWTFromHeader(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {		
			throw new InvalidTokenException("bearer token missing", 404);
		}
		try {
			String token = authorizationHeader.substring("Bearer ".length());
			Algorithm algorithm = Algorithm.HMAC256("secret key".getBytes());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			
			return decodedJWT;
		} catch (TokenExpiredException e) {
			throw new InvalidTokenException("token has expired", 404);
		} catch (Exception e) {
			throw new InvalidTokenException("invalid token", 404);
		}
	}
}
