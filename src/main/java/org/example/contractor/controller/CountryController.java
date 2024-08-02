package org.example.contractor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.service.CountryService;
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
 * REST контроллер для взаимодействия со странами
 */
@Tag(name = "api для взаимодействия со странами")
@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Получает список всех стран
     * @return список всех стран
     */
    @Operation(summary = "Получить список всех стран")
    @ApiResponse(responseCode = "200",
            description = "Список всех стран",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CountryDTO.class))) }
    )
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).USER.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    /**
     * Находит страну по id
     * @param id id станы
     * @return найденная страна
     */
    @Operation(summary = "Найти страну по id")
    @ApiResponse(responseCode = "200",
            description = "Найденная страна",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CountryDTO.class)) }
    )
    @Parameter(description = "id страны")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).USER.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<CountryDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    /**
     * Создает/изменяет страну
     * @param countryDTO страна
     * @return созданная/изменённая страна
     */
    @Operation(summary = "Создать/изменить страну", description = "Если был передан id страны, которая уже создана, " +
            "то она будет изменена, иначе создана")
    @ApiResponse(responseCode = "200",
            description = "Созданная/изменённая страна",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CountryDTO.class)) }
    )
    @PutMapping("/save")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<CountryDTO> createOrUpdate(@RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.save(countryDTO));
    }

    /**
     * Удаляет страну по id
     * @param id id страны
     * @return status code 200
     */
    @Operation(summary = "Удалить страну по id")
    @ApiResponse(responseCode = "200",
            content = { @Content }
    )
    @Parameter(description = "id страны")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable String id) {
        countryService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
