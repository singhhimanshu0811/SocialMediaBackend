package com.ecommerce.project.exceptions;

import java.io.Serial;

//say you want unique category name, see in createCategory function, or any other kind of error in category, which is main
//thing our api deals with
public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public APIException(String message) {
        super(message);
    }

    public APIException() {
    }

}
