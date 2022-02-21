package com.lucazz82.task.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ErrorMessage NotFound(NotFoundException exception) {
		return new ErrorMessage(200, exception.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ErrorMessage badRequest(BadRequestException exception) {
		return new ErrorMessage(201, exception.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ErrorMessage badRequest(MethodArgumentNotValidException exception) {
		List<Violation> error = new ArrayList<>();
		for(FieldError e : exception.getBindingResult().getFieldErrors()) {
			error.add(new Violation(e.getField(), e.getDefaultMessage()));
		}
		
		return new ErrorMessage(201, error.toString());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ErrorMessage badRequest(DataIntegrityViolationException exception) {
		return new ErrorMessage(501, "Incorrect data");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotUniqueUsernameException.class)
	@ResponseBody
	public ErrorMessage badRequest(NotUniqueUsernameException exception) {
		return new ErrorMessage(201, exception.getMessage());
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorMessage ServerError(ServerErrorException exception) {
		return new ErrorMessage(201, exception.getMessage());
	}
}
