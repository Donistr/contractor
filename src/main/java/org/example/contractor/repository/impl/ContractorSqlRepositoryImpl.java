package org.example.contractor.repository.impl;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.example.contractor.repository.ContractorSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс реализует интерфейс {@link ContractorSqlRepository}
 */
@Repository
public class ContractorSqlRepositoryImpl implements ContractorSqlRepository {

    /**
     * Утилитарный класс собирающий ContractorDTO из ResultSet-а
     */
    private static class ContractorRowMapper implements RowMapper<ContractorDTO> {

        @Override
        public ContractorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContractorDTO.ContractorDTOBuilder contractorBuilder = ContractorDTO.builder()
                    .id(rs.getString("contractor_id"))
                    .parentId(rs.getString("contractor_parent_id"))
                    .name(rs.getString("contractor_name"))
                    .fullName(rs.getString("contractor_name_full"))
                    .ogrn(rs.getString("contractor_ogrn"))
                    .inn(rs.getString("contractor_inn"));

            CountryDTO.CountryDTOBuilder countryBuilder = CountryDTO.builder()
                    .id(rs.getString("country_id"))
                    .name(rs.getString("country_name"));
            contractorBuilder.country(countryBuilder.build());

            IndustryDTO.IndustryDTOBuilder industryBuilder = IndustryDTO.builder()
                    .id(rs.getInt("industry_id"))
                    .name(rs.getString("industry_name"));
            contractorBuilder.industry(industryBuilder.build());

            OrgFormDTO.OrgFormDTOBuilder orgFormBuilder = OrgFormDTO.builder()
                    .id(rs.getInt("org_form_id"))
                    .name(rs.getString("org_form_name"));
            contractorBuilder.orgForm(orgFormBuilder.build());

            return contractorBuilder.build();
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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ContractorSqlRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContractorDTO> findAll(SearchContractorRequest request, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(FIND_ACTIVE_CONTRACTORS);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addEqualCondition(queryBuilder, params, "contractor.id", request.getId());
        addEqualCondition(queryBuilder, params, "contractor.parent_id", request.getParentId());
        addLikeCondition(queryBuilder, params, "contractor.name", request.getName());
        addLikeCondition(queryBuilder, params, "contractor.name_full", request.getFullName());
        addLikeCondition(queryBuilder, params, "contractor.inn", request.getInn());
        addLikeCondition(queryBuilder, params, "contractor.ogrn", request.getOgrn());
        CountryDTO country = request.getCountry();
        if (country != null) {
            addLikeCondition(queryBuilder, params, "country.name", country.getName());
        }
        IndustryDTO industry = request.getIndustry();
        if (industry != null) {
            addEqualCondition(queryBuilder, params, "industry.id", industry.getId());
            addLikeCondition(queryBuilder, params, "industry.name", industry.getName());
        }
        OrgFormDTO orgForm = request.getOrgForm();
        if (orgForm != null) {
            addLikeCondition(queryBuilder, params, "org_form.name", orgForm.getName());
        }

        int size = pageable.getPageSize();
        queryBuilder.append("LIMIT ").append(size).append(" OFFSET ").append(size * pageable.getPageNumber());

        return jdbcTemplate.query(queryBuilder.toString(), params, new ContractorRowMapper());
    }

    /**
     * Добавляет в запрос условие equal
     * @param queryBuilder query запроса
     * @param params параметры запроса
     * @param field поле, по которому добавляется условие
     * @param value значение для поля
     */
    private void addEqualCondition(StringBuilder queryBuilder, MapSqlParameterSource params, String field,
                                   Object value) {
        if (value == null) {
            return;
        }

        queryBuilder.append("AND ").append(field).append(" = :").append(field.replace(".", "_"))
                .append(" ");
        params.addValue(field.replace(".", "_"), value);
    }

    /**
     * Добавляет в запрос условие like
     * @param queryBuilder query запроса
     * @param params параметры запроса
     * @param field поле, по которому добавляется условие
     * @param value значение для поля
     */
    private void addLikeCondition(StringBuilder queryBuilder, MapSqlParameterSource params, String field,
                                  String value) {
        if (value == null) {
            return;
        }

        queryBuilder.append("AND ").append(field).append(" LIKE :").append(field.replace(".", "_"))
                .append(" ");
        params.addValue(field.replace(".", "_"), "%" + value + "%");
    }

}
