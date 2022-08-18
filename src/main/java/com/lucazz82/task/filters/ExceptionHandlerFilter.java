package com.lucazz82.task.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucazz82.task.handlers.ErrorMessage;
import com.lucazz82.task.handlers.InvalidTokenException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			int code = 502;
			if(e instanceof InvalidTokenException)
				code = ((InvalidTokenException) e).getCode();
			ErrorMessage error = new ErrorMessage(code, e.getMessage());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getOutputStream(), error);
		}

	}

}
