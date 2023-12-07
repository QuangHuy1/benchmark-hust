package vn.edu.benchmarkhust.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BenchmarkErrorCode implements ErrorCode<HttpStatus> {

    UNKNOWN_ERROR("B100", HttpStatus.BAD_REQUEST, "Unknown error!"),
    ACCESS_DENIED("B101", HttpStatus.FORBIDDEN, "You do not have permission!"),
    INVALID_JSON("B102", HttpStatus.BAD_REQUEST, "Invalid json value"),
    INTERNAL_SERVER("B103", HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something is wrong!"),
     INVALID_DATE_TIME_FORMAT("B124", HttpStatus.BAD_REQUEST, "Invalid DateTime format (yyyy-MM-dd HH:mm:ss)"),
   ;

    private final String code;
    private final HttpStatus httpStatus;
    private String message;

    BenchmarkErrorCode(String code,
                       HttpStatus status,
                       String message) {
        this.code = code;
        this.httpStatus = status;
        this.message = message;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public HttpStatus status() {
        return httpStatus;
    }
}
