package com.example.intuit.businessprofileservice.controller;

import com.example.intuit.businessprofileservice.exception.ValidationNotFoundException;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.service.ValidationService;
import com.example.intuit.businessprofileservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/validation-status")
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @PostMapping("/")
    public ResponseEntity<Validation> createValidationStatus(@RequestBody Validation validation) {
        Validation createdStatus = validationService.createValidationStatus(validation);
        return ResponseEntity.ok(createdStatus);
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<Validation> getValidationStatus(@PathVariable String requestId) {
        Optional<Validation> status = validationService.getValidationStatusById(requestId);
        return status.map(ResponseEntity::ok).orElseThrow(() -> new ValidationNotFoundException(Constants.VALIDATION_NOT_FOUND_MESSAGE));
    }

    @GetMapping("/")
    public ResponseEntity<List<Validation>> getAllValidationStatuses() {
        List<Validation> validations = validationService.getAllValidationStatuses();
        return ResponseEntity.ok(validations);
    }
}
