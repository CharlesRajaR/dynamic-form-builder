package com.zoho.form_builder.service;

import com.zoho.form_builder.modal.Schema;
import com.zoho.form_builder.repository.SchemaRepository;
import com.zoho.form_builder.serviceImpl.SchemaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaService implements SchemaServiceImpl {
    @Autowired
    private SchemaRepository schemaRepository;
    @Override
    public Schema getSchemaByName(String name) throws Exception {
        Schema schema = schemaRepository.findByName(name);
        if(schema == null){
            throw new Exception("Schema not found");
        }
        return schema;
    }

    @Override
    public Schema storeSchema(Schema schema) throws Exception {
        Schema schema1 = schemaRepository.findByName(schema.getName());
        if(schema1 != null){
            throw new Exception("schema already found");
        }
        schema1 = schemaRepository.insert(schema);
        return schema1;
    }

    @Override
    public String deleteById(Long id) {
        schemaRepository.deleteById(id);
        return "deleted schema";
    }
}
