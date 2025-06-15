package com.zoho.form_builder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping
public class MainController {
    @Autowired
    private SchemaService schemaService;
    @Autowired
    private FormDataService formDataService;

    public ResponseEntity<List<Schema>> getAllSchema(){
        List<Schema> schemaList = schemaService.
    }
}
