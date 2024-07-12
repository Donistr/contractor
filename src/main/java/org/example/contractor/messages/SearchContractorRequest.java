package org.example.contractor.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;

@Data
public class SearchContractorRequest {

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
    private CountryDTO country;

    @JsonProperty("industry")
    private IndustryDTO industry;

    @JsonProperty("org_form")
    private OrgFormDTO orgForm;

}
