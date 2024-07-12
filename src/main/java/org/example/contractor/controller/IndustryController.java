package org.example.contractor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "api для взаимодействия с индустриальными кодами")
@RestController
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    @Autowired
    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @Operation(summary = "Получить список всех индустриальных кодов")
    @ApiResponse(responseCode = "200",
            description = "Список всех индустриальных кодов",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = IndustryDTO.class))) }
    )
    @GetMapping("/all")
    public ResponseEntity<List<IndustryDTO>> getAll() {
        return ResponseEntity.ok(industryService.getAll());
    }

    @Operation(summary = "Найти индустриальный код по id")
    @ApiResponse(responseCode = "200",
            description = "Найденный индустриальный код",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = IndustryDTO.class)) }
    )
    @GetMapping("/{id}")
    public ResponseEntity<IndustryDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(industryService.getById(id));
    }

    @Operation(summary = "Создать/изменить индустриальный код", description = "Если был передан id индустриального " +
            "кода, который уже создан, то он будет изменен, иначе создан")
    @ApiResponse(responseCode = "200",
            description = "Созданный/изменённый индустриальный код",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = IndustryDTO.class)) }
    )
    @PutMapping("/save")
    public ResponseEntity<IndustryDTO> createOrUpdate(@RequestBody IndustryDTO countryDTO) {
        return ResponseEntity.ok(industryService.save(countryDTO));
    }

    @Operation(summary = "Удалить индустриальный код по id")
    @ApiResponse(responseCode = "200",
            content = { @Content }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Integer id) {
        industryService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
