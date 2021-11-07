package com.airFrance.offertest.advice;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler{
	
	

	
	@ExceptionHandler
	protected ResponseEntity<String> handleIllggal(IllegalStateException illegalexcpetion)
	{
		return new ResponseEntity<String>("Only adult French residents are allowed to create an account!", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	protected ResponseEntity<Object> handleConstrraint(ConstraintViolationException cve)
	{
		Map<String, String> errors = new HashMap<String, String>();
		cve.getConstraintViolations().forEach((violation) -> {
			String fieldName = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			errors.put(fieldName, message);
		})  ;
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
		 
	}
	
}
