package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * DTO представляющее контрагента
 */
@Data
@Builder
public class ContractorDTO {

    @JsonProperty("id")
    @Schema(description = "id контрагента", example = "id_test")
    private String id;

    @JsonProperty("parent_id")
    @Schema(description = "id родительского контрагента", example = "id_parent")
    private String parentId;

    @JsonProperty("name")
    @Schema(description = "Название контрагента", example = "name_test")
    private String name;

    @JsonProperty("name_full")
    @Schema(description = "Полное название контрагента", example = "name_full_test")
    private String fullName;

    @JsonProperty("inn")
    @Schema(description = "ИНН контрагента", example = "inn_test")
    private String inn;

    @JsonProperty("ogrn")
    @Schema(description = "ОГРН контрагента", example = "ogrn_test")
    private String ogrn;

    @JsonProperty("country")
    @Schema(description = "Страна контрагента", example = "RUS")
    private CountryDTO country;

    @JsonProperty("industry")
    @Schema(description = "Индустриальный код", example = "1")
    private IndustryDTO industry;

    @JsonProperty("org_form")
    @Schema(description = "Организационная форма", example = "1")
    private OrgFormDTO orgForm;

    @JsonProperty("active_main_borrower")
    @Schema(description = "признак наличия сделок, где контрагент является основным заемщиком", example = "true")
    private Boolean activeMainBorrower;

}
