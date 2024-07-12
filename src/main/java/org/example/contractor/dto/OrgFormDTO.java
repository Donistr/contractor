package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrgFormDTO {

    @JsonProperty("id")
    @Schema(description = "id организационной формы", example = "2")
    private Integer id;

    @JsonProperty("name")
    @Schema(description = "Название организационной формы", example = "Автономная некоммерческая организация")
    private String name;

}
