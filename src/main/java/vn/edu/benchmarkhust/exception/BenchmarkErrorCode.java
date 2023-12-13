package vn.edu.benchmarkhust.exception;

import org.springframework.http.HttpStatus;

public enum BenchmarkErrorCode implements ErrorCode<HttpStatus> {

    UNKNOWN_ERROR("B100", HttpStatus.BAD_REQUEST, "Unknown error!"),
    ACCESS_DENIED("B101", HttpStatus.FORBIDDEN, "You do not have permission!"),
    INVALID_JSON("B102", HttpStatus.BAD_REQUEST, "Invalid json value"),
    INTERNAL_SERVER("B103", HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something is wrong!"),
    INVALID_DATE_TIME_FORMAT("B104", HttpStatus.BAD_REQUEST, "Invalid DateTime format (yyyy-MM-dd HH:mm:ss)"),
    NOT_FOUND_ENTITY("B105", HttpStatus.NOT_FOUND, "Not found entity"),
    INVALID_SORT_PARAM("B106", HttpStatus.BAD_REQUEST, "Invalid sort param"),
    DUPLICATE_ENTITY_CODE("B107", HttpStatus.BAD_REQUEST, "Duplicate entity code"),
    EXISTED_VALUE("B108", HttpStatus.BAD_REQUEST, "Exists value"),
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
