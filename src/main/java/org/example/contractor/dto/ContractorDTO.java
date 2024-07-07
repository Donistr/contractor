package org.example.contractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContractorDTO {

    private String id;

    @JsonProperty("parent_id")
    private String parentId;

    private String name;

    @JsonProperty("name_full")
    private String fullName;

    private String inn;

    private String ogrn;

    private CountryDTO country;

    private IndustryDTO industry;

    private OrgFormDTO orgForm;

}
