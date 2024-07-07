package org.example.contractor.controller;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.messages.SearchContractorRequest;
import org.example.contractor.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/search")
    public ResponseEntity<List<ContractorDTO>> getContractors(@RequestBody SearchContractorRequest request,
                                                              Pageable pageable) {
        System.out.println(request);
        return ResponseEntity.ok(contractorService.getContractors(request, pageable));
    }

}
