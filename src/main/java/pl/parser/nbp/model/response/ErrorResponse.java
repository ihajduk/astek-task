package pl.parser.nbp.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int errorCode;
    private String message;
}
