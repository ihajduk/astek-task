package pl.parser.nbp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.parser.nbp.exceptions.UnregisteredDateException;
import pl.parser.nbp.model.response.ErrorResponse;

import java.time.format.DateTimeParseException;
import java.util.Arrays;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnregisteredDateException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(UnregisteredDateException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(DateTimeParseException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Given date could not be parsed.");
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Stack trace: " + Arrays.toString(ex.getStackTrace()));
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }


}