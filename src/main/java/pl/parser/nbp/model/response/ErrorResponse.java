package pl.parser.nbp.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
public class ErrorResponse {
    private int errorCode;
    private String message;
}
