package org.example.contractor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.messages.SearchContractorRequest;
import org.example.contractor.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "api для взаимодействия с контрагентами")
@RestController
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @Operation(summary = "Создать/изменить контрагент", description = "Если был передан id контрагента, " +
            "который уже создан, то он будет изменен, иначе создан")
    @ApiResponse(responseCode = "200",
            description = "Созданный/изменённый индустриальный код",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContractorDTO.class)) }
    )
    @PutMapping("/save")
    public ResponseEntity<ContractorDTO> createOrUpdate(@RequestBody ContractorDTO contractorDTO) {
        return ResponseEntity.ok(contractorService.save(contractorDTO));
    }

    @Operation(summary = "Найти контрагент по id")
    @ApiResponse(responseCode = "200",
            description = "Найденный контрагент",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContractorDTO.class)) }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ContractorDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(contractorService.getById(id));
    }

    @Operation(summary = "Удалить контрагент по id")
    @ApiResponse(responseCode = "200",
            content = { @Content }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable String id) {
        contractorService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

    @Operation(summary = "Получить список всех контрагентов, удовлетворяющих поисковому запросу")
    @ApiResponse(responseCode = "200",
            description = "Список всех контрагентов, удовлетворяющих поисковому запросу",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ContractorDTO.class))) }
    )
    @PostMapping("/search")
    public ResponseEntity<List<ContractorDTO>> getContractors(@RequestBody SearchContractorRequest request,
                                                              Pageable pageable) {
        return ResponseEntity.ok(contractorService.getContractors(request, pageable));
    }

    @Operation(summary = "Функционал аналогичен /search, но реализован через sql")
    @ApiResponse(responseCode = "200",
            description = "Список всех контрагентов, удовлетворяющих поисковому запросу",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ContractorDTO.class))) }
    )
    @PostMapping("/search_sql")
    public ResponseEntity<List<ContractorDTO>> getContractorsSql(@RequestBody SearchContractorRequest request,
                                                              Pageable pageable) {
        return ResponseEntity.ok(contractorService.getContractorsSql(request, pageable));
    }

}
