package pl.parser.nbp.model.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private final int statusCode;
    private final String message;
}
