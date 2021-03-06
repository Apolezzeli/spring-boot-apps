package ar.com.ada.online.second.tpp.advice;

import ar.com.ada.online.second.tpp.advice.validation.RestErrorsResponse;
import ar.com.ada.online.second.tpp.advice.validation.RestFieldError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestValidationExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<RestFieldError> restFieldErrors = fieldErrors
                .stream()
                .map(fieldError -> new RestFieldError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        RestErrorsResponse<RestFieldError> restFieldErrorRestErrorsResponse = new RestErrorsResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                restFieldErrors
        );

        return ResponseEntity.badRequest().body(restFieldErrorRestErrorsResponse);
    }
}