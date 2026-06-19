package br.com.sistema.exception;

public class UnauthorizedAccessException extends AcademicSystemException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}