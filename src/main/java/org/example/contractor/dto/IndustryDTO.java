package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndustryDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

}
