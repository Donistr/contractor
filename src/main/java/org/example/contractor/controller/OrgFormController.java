package org.example.contractor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.service.OrgFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * REST контроллер для взаимодействия с организационными формами
 */
@Tag(name = "api для взаимодействия с организационными формами")
@RestController
@RequestMapping("/org_form")
public class OrgFormController {

    private final OrgFormService orgFormService;

    @Autowired
    public OrgFormController(OrgFormService orgFormService) {
        this.orgFormService = orgFormService;
    }

    /**
     * Получает список всех организационных форм
     * @return список всех организационных форм
     */
    @Operation(summary = "Получить список всех организационных форм")
    @ApiResponse(responseCode = "200",
            description = "Список всех организационных форм",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OrgFormDTO.class))) }
    )
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).USER.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<List<OrgFormDTO>> getAll() {
        return ResponseEntity.ok(orgFormService.getAll());
    }

    /**
     * Находит организационную форму по id
     * @param id id организационной формы
     * @return найденная организационная форма
     */
    @Operation(summary = "Найти организационную форму по id")
    @ApiResponse(responseCode = "200",
            description = "Найденная организационная форма",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrgFormDTO.class)) }
    )
    @Parameter(description = "id организационной формы")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).USER.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<OrgFormDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(orgFormService.getById(id));
    }

    /**
     * Создает/изменяет организационную форму
     * @param countryDTO организационная форма
     * @return созданная/изменённая организационная форма
     */
    @Operation(summary = "Создать/изменить организационную форму", description = "Если был передан id " +
            "организационной формы, которая уже создана, то она будет изменена, иначе создана")
    @ApiResponse(responseCode = "200",
            description = "Созданная/изменённая организационная форма",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrgFormDTO.class)) }
    )
    @PutMapping("/save")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<OrgFormDTO> createOrUpdate(@RequestBody OrgFormDTO countryDTO) {
        return ResponseEntity.ok(orgFormService.save(countryDTO));
    }

    /**
     * Удаляет организационную форму по id
     * @param id id организационной формы
     * @return status code 200
     */
    @Operation(summary = "Удалить организационную форму по id")
    @ApiResponse(responseCode = "200",
            content = { @Content }
    )
    @Parameter(description = "id организационной формы")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Integer id) {
        orgFormService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
