package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * DTO представляющее индустриальный код
 */
@Data
@Builder
public class IndustryDTO {

    @JsonProperty("id")
    @Schema(description = "id индустриального кода", example = "1")
    private Integer id;

    @JsonProperty("name")
    @Schema(description = "Название индустриального кода", example = "Авиастроение")
    private String name;

}
