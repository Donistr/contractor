package org.example.contractor.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "api для взаимодействия с организационными формами")
@RestController
@RequestMapping("/org_form")
public class OrgFormController {

    private final OrgFormService orgFormService;

    @Autowired
    public OrgFormController(OrgFormService orgFormService) {
        this.orgFormService = orgFormService;
    }

    @Operation(summary = "Получить список всех организационных форм")
    @ApiResponse(responseCode = "200",
            description = "Список всех организационных форм",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OrgFormDTO.class))) }
    )
    @GetMapping("/all")
    public ResponseEntity<List<OrgFormDTO>> getAll() {
        return ResponseEntity.ok(orgFormService.getAll());
    }

    @Operation(summary = "Найти организационную форму по id")
    @ApiResponse(responseCode = "200",
            description = "Найденная организационная форма",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrgFormDTO.class)) }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrgFormDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(orgFormService.getById(id));
    }

    @Operation(summary = "Создать/изменить организационную форму", description = "Если был передан id " +
            "организационной формы, которая уже создана, то она будет изменена, иначе создана")
    @ApiResponse(responseCode = "200",
            description = "Созданная/изменённая организационная форма",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrgFormDTO.class)) }
    )
    @PutMapping("/save")
    public ResponseEntity<OrgFormDTO> createOrUpdate(@RequestBody OrgFormDTO countryDTO) {
        return ResponseEntity.ok(orgFormService.save(countryDTO));
    }

    @Operation(summary = "Удалить организационную форму по id")
    @ApiResponse(responseCode = "200",
            content = { @Content }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Integer id) {
        orgFormService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
