package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * DTO представляющее страну
 */
@Data
@Builder
public class CountryDTO {

    @JsonProperty("id")
    @Schema(description = "id страны", example = "ABH")
    private String id;

    @JsonProperty("name")
    @Schema(description = "Название страны", example = "Абхазия")
    private String name;

}
