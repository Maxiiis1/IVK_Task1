package org.example.exceptions;

public class NoMoveAvailableException extends RuntimeException {
    public NoMoveAvailableException(String message) {
        super(message);
    }
}
