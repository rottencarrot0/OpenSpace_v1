package openspace.page.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException() {
        super("파일 업로드중 예외가 발생했습니다.");
    }
}
