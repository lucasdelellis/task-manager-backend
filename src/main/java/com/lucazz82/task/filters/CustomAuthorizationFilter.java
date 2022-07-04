package com.lucazz82.task.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucazz82.task.handlers.AuthorizationException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
		
		if(path.equals("/login")) {
			filterChain.doFilter(request, response);
		} else if(path.equals("/user") && request.getMethod().equals("POST")) {
			filterChain.doFilter(request, response);
		} else {
//			try {
				String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
				if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret key".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject(); // Obtain username
					// Here we can collect roles if correspond
					UsernamePasswordAuthenticationToken authenticationToken = 
							new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);				
				} else {
					filterChain.doFilter(request, response);
				}
//			} catch (Exception e) {
//				Map<String, String> error = new HashMap<>();
//				error.put("message", e.getMessage());
//				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//				ObjectMapper objectMapper = new ObjectMapper();
//				objectMapper.writeValue(response.getOutputStream(), error);
//				response.setStatus(HttpStatus.FORBIDDEN.value());
//			}

		}

	}

}
