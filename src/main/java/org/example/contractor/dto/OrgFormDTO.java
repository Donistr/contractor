package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrgFormDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

}
