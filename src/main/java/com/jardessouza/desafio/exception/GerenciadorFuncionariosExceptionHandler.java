package com.jardessouza.desafio.exception;

import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GerenciadorFuncionariosExceptionHandler {
    @RestControllerAdvice
    public class GerenciadorPessoasExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(FeignException.class)
        public ResponseEntity<Object> handlerFeignException(FeignException exception) {
            buildResponseEntity(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage(),
                    Collections.singletonList(exception.getMessage()));

            return buildResponseEntity(HttpStatus.BAD_REQUEST,
                    "CEP code does not exist in the ViaCEP database",
                    Collections.singletonList(exception.getLocalizedMessage()));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Object> handlerEntityNotFoundException(EntityNotFoundException exception) {
            return buildResponseEntity(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage(),
                    Collections.singletonList(exception.getMessage()));
        }

        @ExceptionHandler(EntityExistsException.class)
        public ResponseEntity<Object> handlerEntityExistsException(EntityExistsException exception) {
            return buildResponseEntity(
                    HttpStatus.CONFLICT,
                    exception.getMessage(),
                    Collections.singletonList(exception.getMessage()));
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException exception,
                HttpHeaders headers,
                HttpStatus status,
                WebRequest request) {

            List<String> errors = new ArrayList<>();
            exception.getBindingResult().getFieldErrors()
                    .forEach(fieldError -> errors.add("Field " + fieldError.getField().toUpperCase() +
                            " " + fieldError.getDefaultMessage()));

            exception.getBindingResult().getGlobalErrors()
                    .forEach(globalErrors -> errors.add("Object " + globalErrors.getObjectName() +
                            " " + globalErrors.getDefaultMessage()));

            return buildResponseEntity(HttpStatus.BAD_REQUEST, "Report validation errors of the argument(s)", errors);
        }

        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(
                HttpMessageNotReadableException exception,
                HttpHeaders headers,
                HttpStatus status,
                WebRequest request) {

            return buildResponseEntity(HttpStatus.BAD_REQUEST,
                    "Wrong JSON body and/or error(s) filed",
                    Collections.singletonList(exception.getLocalizedMessage()));
        }

        private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
            ApiError apiError = ApiError.builder()
                    .code(httpStatus.value())
                    .status(httpStatus.getReasonPhrase())
                    .message(message)
                    .errors(errors)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(httpStatus).body(apiError);
        }
    }
}