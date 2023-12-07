package vn.edu.benchmarkhust.exception;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;

public class ErrorCodeException extends RuntimeException {
    private final String id = RandomStringUtils.randomAlphabetic(5);
    private final String message;
    private final transient ErrorCode<?> errorCode;
    private final transient JsonNode description;

    public ErrorCodeException(ErrorCode<?> errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.message();
        this.description = null;
    }

    public ErrorCodeException(ErrorCode<?> errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
        this.description = null;
    }

    public ErrorCodeException(Throwable cause, ErrorCode<?> errorCode, String message, JsonNode description) {
        super(cause);
        this.message = message;
        this.errorCode = errorCode;
        this.description = description;
    }

    public ErrorCodeException(ErrorCode<?> errorCode, String message, JsonNode description) {
        this.message = message;
        this.errorCode = errorCode;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public ErrorCode<?> getErrorCode() {
        return this.errorCode;
    }

    public JsonNode getDescription() {
        return this.description;
    }

    public String toString() {
        String var10000 = this.getId();
        return "ErrorCodeException(id=" + var10000 + ", message=" + this.getMessage() + ", errorCode=" + this.getErrorCode() + ", description=" + this.getDescription() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ErrorCodeException)) {
            return false;
        } else {
            ErrorCodeException other = (ErrorCodeException) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ErrorCodeException;
    }

    public int hashCode() {
        int result = super.hashCode();
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }
}