package pl.parser.nbp.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import pl.parser.nbp.model.response.ErrorResponse;
import pl.parser.nbp.validators.CurrencyValidator;

@ControllerAdvice
public class ExceptionControllerAdvice {

    Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    CurrencyValidator currencyValidator;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.setValidator(currencyValidator);
    }
}