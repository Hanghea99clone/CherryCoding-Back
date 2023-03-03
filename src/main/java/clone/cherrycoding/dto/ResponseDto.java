package clone.cherrycoding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ResponseDto<T> success(T data) {

        return new ResponseDto<>(200, data);
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private final int statusCode;
        private final String message;
    }
}
