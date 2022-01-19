package pj.circles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class NoSuchElement {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> noSuch(NoSuchElementException e){
        ErrorResult errorResult = new ErrorResult("NoSuch-Element",e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }
}
