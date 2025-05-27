package su.library.exception;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LibraryExceptionHandler.class)
    public ResponseEntity<String> handleBookExists(LibraryExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // @ExceptionHandler(MaxUploadSizeExceededException.class)
    // public ResponseEntity<String>
    // handleMaxSizeException(MaxUploadSizeExceededException exc) {
    // return ResponseEntity
    // .status(HttpStatus.PAYLOAD_TOO_LARGE)
    // .body(exc.getMessage());
    // }

    @ExceptionHandler(value = SizeFileUploadException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ErrorResponse handleSizeFileUploadException(SizeFileUploadException ex) {
        return new ErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), ex.getMessage());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBookAlreadyExistsException(AlreadyExistsException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = NoFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoFoundBookException(NoFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}