package vn.edu.benchmarkhust.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private String message;
    @JsonProperty("description")
    private JsonNode description;

    public ErrorResponse() {
    }

    public ErrorResponse(BenchmarkErrorCode errorCode) {
        this.error = errorCode.code();
        this.message = errorCode.message();
        this.id = RandomStringUtils.randomAlphabetic(5);
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }

    public ErrorResponse(String id, String error, String message) {
        this.id = id;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String id, String error, String message, JsonNode description) {
        this.id = id;
        this.error = error;
        this.message = message;
        this.description = description;
    }

    public String toString() {
        String var10000 = this.getId();
        return "ErrorResponse(id=" + var10000 + ", error=" + this.getError() + ", message=" + this.getMessage() + ", description=" + this.getDescription() + ")";
    }
}
