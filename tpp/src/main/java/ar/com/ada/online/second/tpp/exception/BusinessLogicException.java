package ar.com.ada.online.second.tpp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Setter
@Getter
public class BusinessLogicException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;
    private List<EntityError> entityErrors = new ArrayList<>();

    public BusinessLogicException(String message, HttpStatus httpStatus, EntityError entityError) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.entityErrors.add(entityError);
    }
}