package com.ecommerce.project.exceptions;

import com.ecommerce.project.paylod.APIResponse;
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

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    //can also do exception.class for generic exception
//    public ResponseEntity<Map<String, String>>myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        Map<String, String>response = new HashMap<>();
//        e.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError)error).getField();
//            String errorMessage = error.getDefaultMessage();
//            response.put(fieldName, errorMessage);
//        });
//        //return response;
//        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
//    }
@ExceptionHandler(MethodArgumentNotValidException.class)
//can also do exception.class for generic exception
public ResponseEntity<Map<String, APIResponse>>myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    Map<String, APIResponse>response = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach(error -> {
        String fieldName = ((FieldError)error).getField();
        String errorMessage = error.getDefaultMessage();
        response.put(fieldName, new APIResponse(errorMessage, false));
    });
    //return response;
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
}
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<String>myResourceNotFoundException(ResourceNotFoundException e){
//        String errorMessage = e.getMessage();
//        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse>myResourceNotFoundException(ResourceNotFoundException e){
        APIResponse response = new APIResponse(e.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(APIException.class)
//    public ResponseEntity<String>myAPIException(APIException e){
//        String errorMessage = e.getMessage();
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse>myAPIException(APIException e){
        APIResponse response = new APIResponse(e.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
