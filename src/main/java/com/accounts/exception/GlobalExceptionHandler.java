package com.accounts.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.accounts.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	//for Handling validation related exceptions
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//		System.out.println("Exception :::"+ex.getStackTrace().toString());
		Map<String, String> exceptionMap = new HashMap<>();
		List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
		
		validationErrorList.forEach((error)-> {
			String field = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			exceptionMap.put(field, defaultMessage);
		});
		
		return new ResponseEntity<>(exceptionMap,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest request){
//		System.out.println("Exception :::"+exception.getStackTrace().toString());
		ErrorResponseDto errorresponse = new ErrorResponseDto(request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(errorresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception, WebRequest request){
		ErrorResponseDto errorresponse = new ErrorResponseDto(request.getDescription(false), HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(errorresponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		ErrorResponseDto errorresponse = new ErrorResponseDto(request.getDescription(false), HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(errorresponse, HttpStatus.NOT_FOUND);
	}
}
