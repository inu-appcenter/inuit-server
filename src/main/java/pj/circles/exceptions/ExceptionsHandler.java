package pj.circles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.NonUniqueResultException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> notValid(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult("400", "잘못된요청입니다");
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResult.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> noSuch(NoSuchElementException e) {
        ErrorResult errorResult = new ErrorResult("NoSuch-Element", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalArgument(IllegalArgumentException e) {
        ErrorResult errorResult = new ErrorResult("Illegal-Argument", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResult> nullPointer(NullPointerException e) {
        ErrorResult errorResult = new ErrorResult("Null-Point", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResult> accessDenied(AccessDeniedException e) {
        ErrorResult errorResult = new ErrorResult("Forbidden", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<ErrorResult> accessDenied(NonUniqueResultException e) {
        ErrorResult errorResult = new ErrorResult("Non-Unique", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }
}
