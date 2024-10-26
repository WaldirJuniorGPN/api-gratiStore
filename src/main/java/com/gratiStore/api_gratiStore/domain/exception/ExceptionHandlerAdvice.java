package com.gratiStore.api_gratiStore.domain.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

import static com.gratiStore.api_gratiStore.domain.utils.ConstantesUtils.*;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final StandardError error = new StandardError();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGeneralException(Exception exception, HttpServletRequest request) {
        return this.atribuirError(exception, HttpStatus.INTERNAL_SERVER_ERROR, ERRO_INESPERADO, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException exception, HttpServletRequest request) {
        return this.atribuirError(exception, HttpStatus.NOT_FOUND, ENTIDADE_NAO_ENCONTRADA, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        return this.atribuirError(exception, HttpStatus.BAD_REQUEST, PARAMETROS_INVALIDOS, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return this.atribuirError(exception, HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        return this.atribuirError(exception, HttpStatus.BAD_REQUEST, ERRO_VALIDACAO, request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<StandardError> handleBindException(BindException exception, HttpServletRequest request) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return this.atribuirError(exception, HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    private ResponseEntity<StandardError> atribuirError(Exception e, HttpStatus status, String msgError, HttpServletRequest request) {
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(msgError);
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
