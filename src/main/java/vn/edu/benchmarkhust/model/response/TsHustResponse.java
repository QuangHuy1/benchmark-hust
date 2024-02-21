package vn.edu.benchmarkhust.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TsHustResponse {
    private String status;
    private Data data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty(value = "point_dgtd")
        private String pointDgtd;

        @JsonProperty(value = "point_tn")
        private String pointTn;

        @JsonProperty(value = "year")
        private String year;
    }
}
