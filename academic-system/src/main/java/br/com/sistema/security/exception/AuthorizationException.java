package br.com.sistema.security.exception;

public class AuthorizationException extends SecurityException {
    public AuthorizationException(String message) {
        super(message);
    }
}