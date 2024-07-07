package org.example.contractor.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;

@Data
public class SearchContractorRequest {

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
