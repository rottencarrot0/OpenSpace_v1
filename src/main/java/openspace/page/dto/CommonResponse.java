package openspace.page.dto;

import lombok.Getter;

@Getter
public class CommonResponse {

    private String message;
    private String status;
    private Object data;

    public CommonResponse(String message, String status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static CommonResponse error(String message) {
        return new CommonResponse(message, "error", null);
    }

    public static CommonResponse success(Object data) {
        return new CommonResponse(null, "success", data);
    }

    public static CommonResponse success(String message, Object data) {
        return new CommonResponse(null, "success", data);
    }
}
