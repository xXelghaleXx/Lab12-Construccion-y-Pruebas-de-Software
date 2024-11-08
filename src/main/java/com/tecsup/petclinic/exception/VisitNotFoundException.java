package com.tecsup.petclinic.exception;

/**
 * Visit Not Found Exception
 */
public class VisitNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public VisitNotFoundException(String message) {
        super(message);
    }
}
