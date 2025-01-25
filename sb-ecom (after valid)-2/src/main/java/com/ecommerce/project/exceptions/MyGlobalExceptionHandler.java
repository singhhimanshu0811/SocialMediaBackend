package com.ecommerce.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice//capturing any exception by any controller

public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //can also do exception.class for generic exception
    public ResponseEntity<Map<String, String>>myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String>response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
//see you can put any string here, that'll have highest priority. next will be string with @notblank annotation(or any other)
            //lowest will be common default message
            response.put(fieldName, errorMessage);
            //that's why in body, you get json, category name: no blank and so on, as we pass field name in json also
        });
        //return response;
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        //if you just return response, we'll get string error in body but status code will be 200 as exception is being handled
        //we dont want that
    }
    //implementing in place of responseEntityException which we have used in impl in previous version
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String>myResourceNotFoundException(ResourceNotFoundException e){
        String errorMessage = e.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String>myAPIException(APIException e){
        String errorMessage = e.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
