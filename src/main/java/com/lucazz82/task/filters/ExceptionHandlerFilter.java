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

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ErrorMessage error = new ErrorMessage(502, e.getMessage());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getOutputStream(), error);
			response.setStatus(HttpStatus.FORBIDDEN.value());
		}

	}

}
