package org.example.contractor.controller;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.service.CountryService;
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
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @PutMapping("/save")
    public ResponseEntity<CountryDTO> createOrUpdate(@RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.save(countryDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable String id) {
        countryService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
