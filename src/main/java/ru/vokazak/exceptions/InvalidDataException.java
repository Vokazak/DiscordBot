package ru.vokazak.exceptions;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(Exception e) {
        super(e);
    }

    public InvalidDataException(String message, Exception e) {
        super(message, e);
    }
}
