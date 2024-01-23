package vn.edu.benchmarkhust.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SchoolRequest {

    @NotBlank(message = "Required vnName")
    private String vnName;

    @NotBlank(message = "Required enName")
    private String enName;

    @NotBlank(message = "Required abbreviations")
    private String abbreviations;

}
