package pj.circles.dto;

import lombok.Data;

@Data
public class EmailResponse {

    private String response;
    public EmailResponse(String email){
        this.response =email;
    }
}
