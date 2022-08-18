package com.lucazz82.task.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();

		if (!path.startsWith("/auth")) {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret key".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(token);
				String username = decodedJWT.getSubject(); // Obtain username

				// Obtain roles
				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} else {
				throw new ServletException("Invalid token");
			}
		}

		filterChain.doFilter(request, response);

	}

//	private boolean isProtected(String path, String method) {
//		boolean response = true;
//		
//		if(path.equals("/login")) {
//			response = false;
//		} 
//		
//		if(path.equals("/user") && method.equals(HttpMethod.POST.name())) {
//			response = false;
//		}
//		
//		return response;
//	}

//	private boolean isProtected(String path) {		
//		return !;
//	}
}
