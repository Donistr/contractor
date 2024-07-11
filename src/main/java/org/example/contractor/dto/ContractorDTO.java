package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractorDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_full")
    private String fullName;

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("ogrn")
    private String ogrn;

    @JsonProperty("country")
    private CountryDTO country;

    @JsonProperty("industry")
    private IndustryDTO industry;

    @JsonProperty("org_form")
    private OrgFormDTO orgForm;

}
