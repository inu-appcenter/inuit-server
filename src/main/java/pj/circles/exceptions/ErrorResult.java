package pj.circles.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class ErrorResult {
    private final String code;
    private final String message;

    private Map<String,String> validation=new HashMap<>();

    public void addValidation(String fieldName,String errorMessage){
        this.validation.put(fieldName,errorMessage);
    }


}
