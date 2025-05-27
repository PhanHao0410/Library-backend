package su.library.exception;

public class SizeFileUploadException extends RuntimeException {
    private String message;

    public SizeFileUploadException() {
    }

    public SizeFileUploadException(String msg) {
        super(msg);
        this.message = msg;
    }
}
