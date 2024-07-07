package org.example.contractor.controller;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.ResponseObject;
import org.example.contractor.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    @Autowired
    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<IndustryDTO>> getAll() {
        return ResponseEntity.ok(industryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(industryService.getById(id));
    }

    @PutMapping("/save")
    public ResponseEntity<IndustryDTO> createOrUpdate(@RequestBody IndustryDTO countryDTO) {
        return ResponseEntity.ok(industryService.save(countryDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Integer id) {
        industryService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
