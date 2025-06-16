package com.zoho.form_builder.controller;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;
import com.zoho.form_builder.serviceImpl.FormDataServiceImpl;
import com.zoho.form_builder.serviceImpl.SchemaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class MainController {
    @Autowired
    private SchemaServiceImpl schemaService;
    @Autowired
    private FormDataServiceImpl formDataService;

    @GetMapping("/get-all-schema")
    public ResponseEntity<List<Schema>> getAllSchema(){
        List<Schema> schemaList = schemaService.findAllSchema();

        return new ResponseEntity<>(schemaList, HttpStatus.OK);
    }

    @GetMapping("/get-form-data/{schemaName}")
    public ResponseEntity<List<FormData>> getAllFormDataOfSchema(@PathVariable String schemaName) throws Exception {
        Schema schema = schemaService.getSchemaByName(schemaName);

        List<FormData> formData = formDataService.getFormDataBySchema(schema);

        return new ResponseEntity<>(formData, HttpStatus.OK);
    }

    @GetMapping("/get-schema/{schemaName}")
    public ResponseEntity<Schema> getSchemaByName(@PathVariable String schemaName) throws Exception {
        Schema schema = schemaService.getSchemaByName(schemaName);

        return new ResponseEntity<>(schema, HttpStatus.OK);
    }

    @PostMapping("/store/schema")
    public ResponseEntity<Schema> storeSchema(@RequestBody Schema schema) throws Exception {
        Schema schema1 = schemaService.storeSchema(schema);

        return new ResponseEntity<>(schema1, HttpStatus.CREATED);
    }

    @PostMapping("/store-form-data/{schemaName}")
    public ResponseEntity<FormData> storeFormData(@PathVariable String schemaName,
                                                  @RequestBody Map<String, String> data) throws Exception {
        FormData formData = new FormData();
        Schema schema = schemaService.getSchemaByName(schemaName);
        formData.setSchema(schema);
        formData.setFieldWithValues(data);

        FormData savedFormData = formDataService.storeFormData(formData);

        return new ResponseEntity<>(savedFormData, HttpStatus.CREATED);
    }
}
