package vn.edu.benchmarkhust.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.exception.ErrorResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ErrorResponse> handleErrorCodeException(ErrorCodeException ex) {
        log.error(ex.getMessage(), ex);
        return toErrorResponse(ex);
    }

    @ExceptionHandler(DecodingException.class)
    public ResponseEntity<ErrorResponse> handleDecodingException(DecodingException ex) {
        log.error(ex.getMessage(), ex);
        return toErrorResponse(BenchmarkErrorCode.INVALID_JSON, ex);
    }

    @ExceptionHandler({JsonMappingException.class})
    public ResponseEntity<ErrorResponse> handleJsonMappingException(JsonMappingException ex) {
        log.error(ex.getMessage(), ex);
        if (ex.getCause().getClass() == DateTimeParseException.class) {
            return toErrorResponse(BenchmarkErrorCode.INVALID_DATE_TIME_FORMAT);
        }

        return toErrorResponse(BenchmarkErrorCode.INVALID_JSON, ex);
    }

    @ExceptionHandler({JsonProcessingException.class})
    public ResponseEntity<ErrorResponse> handleJsonProcessingException(JsonProcessingException ex) {
        log.error(ex.getMessage(), ex);
        return toErrorResponse(BenchmarkErrorCode.INVALID_JSON, ex);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        return toErrorResponse(BenchmarkErrorCode.INTERNAL_SERVER, ex);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
//        log.error(ex.getMessage(), ex);
//        return toErrorResponse(BenchmarkErrorCode.ACCESS_DENIED, ex);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgNotValid(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        try {
            var errorEnum = BenchmarkErrorCode.valueOf(ex.getFieldError().getDefaultMessage());
            return toErrorResponse(errorEnum);
        } catch (Exception e) {
            return toErrorResponse(BenchmarkErrorCode.UNKNOWN_ERROR);
        }
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(BindException ex) {
        log.error(ex.getMessage(), ex);
        if (ex.getFieldType(ex.getFieldError().getField()) == LocalDateTime.class) {
            return toErrorResponse(BenchmarkErrorCode.INVALID_DATE_TIME_FORMAT);
        }

        var id = RandomStringUtils.randomAlphabetic(5);
        return toErrorResponse(id, "Binding error exception", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(String id,
                                                         String error,
                                                         String message,
                                                         HttpStatus status) {
        return toResponseEntity(new ErrorResponse(id, error, message), status);
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(String id,
                                                         String error,
                                                         String message,
                                                         JsonNode description,
                                                         HttpStatus status) {
        return toResponseEntity(new ErrorResponse(id, error, message, description), status);
    }

    @SuppressWarnings("all")
    public ResponseEntity<ErrorResponse> toErrorResponse(ErrorCodeException ex) {
        var errorCode = (ErrorCode<HttpStatus>) ex.getErrorCode();
        var errorResp = new ErrorResponse(ex.getId(), errorCode.code(), ex.getMessage(), ex.getDescription());
        return toResponseEntity(errorResp, errorCode.status());
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(BenchmarkErrorCode errorCode, Throwable ex) {
        var id = RandomStringUtils.randomAlphabetic(5);
        var errorResp = new ErrorResponse(id, errorCode.code(), ex.getMessage());
        return toResponseEntity(errorResp, errorCode.status());
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(BenchmarkErrorCode errorCode) {
        var id = RandomStringUtils.randomAlphabetic(5);
        var errorResp = new ErrorResponse(id, errorCode.code(), errorCode.message());
        return toResponseEntity(errorResp, errorCode.status());
    }

    private <T> ResponseEntity<T> toResponseEntity(T data, HttpStatus status) {
        return new ResponseEntity(data, status);
    }

}
