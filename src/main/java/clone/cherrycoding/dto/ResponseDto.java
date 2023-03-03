package clone.cherrycoding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Error error;

    public static <T> ResponseDto<T> success(T data) {

        return new ResponseDto<>(true, data, null);
    }
    public static <T> ResponseDto<T> fail(int status, HttpStatus httpStatus, String message){
        return new ResponseDto<>(false, null, new Error(status, httpStatus, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private final int status;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
