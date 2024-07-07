package org.example.contractor.controller;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.dto.ResponseObject;
import org.example.contractor.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @PutMapping("/save")
    public ResponseEntity<ContractorDTO> createOrUpdate(@RequestBody ContractorDTO contractorDTO) {
        return ResponseEntity.ok(contractorService.save(contractorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractorDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(contractorService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable String id) {
        contractorService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
