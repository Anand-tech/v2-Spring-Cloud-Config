package com.eazybytes.cards.exception;


import com.eazybytes.cards.dto.ErrorResponseDto;
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
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,HttpStatusCode statusCode,WebRequest webRequest){

        Map<String ,String> map = new HashMap<>();
        List<ObjectError> validationErrorList = exception.getBindingResult().getAllErrors();

        validationErrorList.forEach((error)->{
            String validationError = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            map.put(validationError,errorMessage);
        });

        return new ResponseEntity<>(validationErrorList,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(CardsAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCardsAlreadyExistsException(CardsAlreadyExistsException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.BAD_REQUEST, exception.getMessage(),LocalDateTime.now());

        return  new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>  handleGlobalException(Exception exception , WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.BAD_REQUEST,exception.getMessage(),LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);

    }

}
