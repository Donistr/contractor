package org.example.contractor.controller;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.messages.ResponseObject;
import org.example.contractor.service.OrgFormService;
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
@RequestMapping("/org_form")
public class OrgFormController {

    private final OrgFormService orgFormService;

    @Autowired
    public OrgFormController(OrgFormService orgFormService) {
        this.orgFormService = orgFormService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrgFormDTO>> getAll() {
        return ResponseEntity.ok(orgFormService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgFormDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(orgFormService.getById(id));
    }

    @PutMapping("/save")
    public ResponseEntity<OrgFormDTO> createOrUpdate(@RequestBody OrgFormDTO countryDTO) {
        return ResponseEntity.ok(orgFormService.save(countryDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Integer id) {
        orgFormService.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
