package org.example.contractor.repository.impl;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.example.contractor.repository.ContractorSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContractorSqlRepositoryImpl implements ContractorSqlRepository {

    private static class ContractorRowMapper implements RowMapper<ContractorDTO> {

        @Override
        public ContractorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContractorDTO contractor = new ContractorDTO();
            contractor.setId(rs.getString("contractor_id"));
            contractor.setParentId(rs.getString("contractor_parent_id"));
            contractor.setName(rs.getString("contractor_name"));
            contractor.setFullName(rs.getString("contractor_name_full"));
            contractor.setOgrn(rs.getString("contractor_ogrn"));
            contractor.setInn(rs.getString("contractor_inn"));

            CountryDTO country = new CountryDTO();
            country.setId(rs.getString("country_id"));
            country.setName(rs.getString("country_name"));
            contractor.setCountry(country);

            IndustryDTO industry = new IndustryDTO();
            industry.setId(rs.getInt("industry_id"));
            industry.setName(rs.getString("industry_name"));
            contractor.setIndustry(industry);

            OrgFormDTO orgForm = new OrgFormDTO();
            orgForm.setId(rs.getInt("org_form_id"));
            orgForm.setName(rs.getString("org_form_name"));
            contractor.setOrgForm(orgForm);

            return contractor;
        }

    }

    private static final String FIND_ACTIVE_CONTRACTORS = """
            SELECT contractor.id AS contractor_id, contractor.parent_id AS contractor_parent_id,
            contractor.name AS contractor_name, contractor.name_full AS contractor_name_full,
            contractor.ogrn AS contractor_ogrn, contractor.inn AS contractor_inn,
            country.id AS country_id, country.name AS country_name, industry.id AS industry_id,
            industry.name AS industry_name, org_form.id AS org_form_id, org_form.name AS org_form_name
            FROM contractor
            LEFT JOIN country country ON contractor.country = country.id
            LEFT JOIN industry industry ON contractor.industry = industry.id
            LEFT JOIN org_form org_form ON contractor.org_form = org_form.id
            WHERE contractor.is_active = true
           """;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContractorSqlRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ContractorDTO> findAll(SearchContractorRequest request, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(FIND_ACTIVE_CONTRACTORS);

        addEqualCondition(queryBuilder, "contractor.id", request.getId());
        addEqualCondition(queryBuilder, "contractor.parent_id", request.getParentId());
        addLikeCondition(queryBuilder, "contractor.name", request.getName());
        addLikeCondition(queryBuilder, "contractor.name_full", request.getFullName());
        addLikeCondition(queryBuilder, "contractor.inn", request.getInn());
        addLikeCondition(queryBuilder, "contractor.ogrn", request.getOgrn());
        CountryDTO country = request.getCountry();
        if (country != null) {
            addLikeCondition(queryBuilder, "country.name", country.getName());
        }
        IndustryDTO industry = request.getIndustry();
        if (industry != null) {
            addEqualCondition(queryBuilder, "industry.id", industry.getId());
            addLikeCondition(queryBuilder, "industry.name", industry.getName());
        }
        OrgFormDTO orgForm = request.getOrgForm();
        if (orgForm != null) {
            addLikeCondition(queryBuilder, "org_form.name", orgForm.getName());
        }

        int size = pageable.getPageSize();
        queryBuilder.append("LIMIT ").append(size).append(" OFFSET ").append(size * pageable.getPageNumber());

        return jdbcTemplate.query(queryBuilder.toString(), new ContractorRowMapper());
    }

    private void addEqualCondition(StringBuilder queryBuilder, String field, Object value) {
        if (value == null) {
            return;
        }

        queryBuilder.append("AND ").append(field).append(" = '").append(value).append("' ");
    }

    private void addLikeCondition(StringBuilder queryBuilder, String field, String value) {
        if (value == null) {
            return;
        }

        queryBuilder.append("AND ").append(field).append(" LIKE '%").append(value).append("%' ");
    }

}
