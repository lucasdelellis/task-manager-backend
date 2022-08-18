package com.lucazz82.task.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	@ResponseBody
	public ErrorMessage forbidden(ForbiddenException exception) {
		return new ErrorMessage(201, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ErrorMessage badRequest(BadRequestException exception) {
		return new ErrorMessage(202, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ErrorMessage badRequest(MethodArgumentNotValidException exception) {
		List<Violation> errors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		for (FieldError e : fieldErrors) {
			errors.add(new Violation(e.getField(), e.getDefaultMessage()));
		}

		return new ErrorMessage(203, errors.toString());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ErrorMessage badRequest(DataIntegrityViolationException exception) {
		return new ErrorMessage(204, "Incorrect data");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotUniqueUsernameException.class)
	@ResponseBody
	public ErrorMessage badRequest(NotUniqueUsernameException exception) {
		return new ErrorMessage(205, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ErrorMessage badRequest(HttpMessageNotReadableException exception) {
		String message = exception.getMessage().split(":")[0];
		return new ErrorMessage(206, message);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorMessage ServerError(Exception exception) {
		return new ErrorMessage(500, "internal server error");
	}
}
