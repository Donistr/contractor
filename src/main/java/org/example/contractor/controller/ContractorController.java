package org.example.contractor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.dto.SetMainBorrowerDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.messages.SearchContractorRequest;
import org.example.contractor.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * REST контроллер для взаимодействия с контрагентами
 */
@Tag(name = "api для взаимодействия с контрагентами")
@RestController
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    /**
     * Создаёт/изменяет контрагента
     * @param contractorDTO контрагент
     * @return созданный/изменённый контрагент
     */
    @Operation(summary = "Создать/изменить контрагент", description = "Если был передан id контрагента, " +
            "который уже создан, то он будет изменен, иначе создан")
    @ApiResponse(responseCode = "200",
            description = "Созданный/изменённый контрагент",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContractorDTO.class)) }
    )
    @PutMapping("/save")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<ContractorDTO> createOrUpdate(@RequestBody ContractorDTO contractorDTO) {
        return ResponseEntity.ok(contractorService.save(contractorDTO));
    }

    /**
     * Находит контрагент по id
     * @param id id контрагента
     * @return найденный контрагент
     */
    @Operation(summary = "Найти контрагент по id")
    @ApiResponse(responseCode = "200",
            description = "Найденный контрагент",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContractorDTO.class)) }
    )
    @Parameter(description = "id контрагента")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).USER.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<ContractorDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(contractorService.getById(id));
    }

    /**
     * Удаляет контрагент по id
     * @param id id контрагента
     * @return status code 200
     */
    @Operation(summary = "Удалить контрагент по id")
    @ApiResponse(responseCode = "200",
            content = { @Content }
    )
    @Parameter(description = "id контрагента")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable String id) {
        contractorService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

    /**
     * Получает список всех контрагентов, удовлетворяющих поисковому запросу
     * @param request поисковый запрос
     * @param pageable параметры пагинации
     * @return список всех контрагентов, удовлетворяющих поисковому запросу
     */
    @Operation(summary = "Получить список всех контрагентов, удовлетворяющих поисковому запросу")
    @ApiResponse(responseCode = "200",
            description = "Список всех контрагентов, удовлетворяющих поисковому запросу",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ContractorDTO.class))) }
    )
    @PostMapping("/search")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<List<ContractorDTO>> getContractors(@RequestBody SearchContractorRequest request,
                                                              Pageable pageable) {
        return ResponseEntity.ok(contractorService.getContractors(request, pageable));
    }

    /**
     * Получает список всех контрагентов, удовлетворяющих поисковому запросу
     * @param request поисковый запрос
     * @param pageable параметры пагинации
     * @return список всех контрагентов, удовлетворяющих поисковому запросу
     */
    @Operation(summary = "Функционал аналогичен /search, но реализован через sql")
    @ApiResponse(responseCode = "200",
            description = "Список всех контрагентов, удовлетворяющих поисковому запросу",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ContractorDTO.class))) }
    )
    @PostMapping("/search_sql")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_RUS.value, " +
            "T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<List<ContractorDTO>> getContractorsSql(@RequestBody SearchContractorRequest request,
                                                              Pageable pageable) {
        return ResponseEntity.ok(contractorService.getContractorsSql(request, pageable));
    }

    /**
     * Выставляет заданному контрагенту признак наличия сделок, где он является основным заёмщиком
     * @param setMainBorrowerDTO запрос
     * @return контрагент
     */
    @Operation(summary = "Установить контрагенту признак наличия сделок, где он является основным заёмщиком")
    @ApiResponse(responseCode = "200",
            description = "Контрагент с установленным признаком",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContractorDTO.class)) }
    )
    @PatchMapping("/main-borrower")
    @PreAuthorize("hasAnyAuthority(T(org.example.auth.role.RoleEnum).CONTRACTOR_SUPERUSER.value, " +
            "T(org.example.auth.role.RoleEnum).SUPERUSER.value)")
    public ResponseEntity<ContractorDTO> setMainBorrower(@RequestBody SetMainBorrowerDTO setMainBorrowerDTO) {
        return ResponseEntity.ok(contractorService.setMainBorrower(setMainBorrowerDTO));
    }

}
