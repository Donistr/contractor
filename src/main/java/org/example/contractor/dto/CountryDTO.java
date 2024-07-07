package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

}
