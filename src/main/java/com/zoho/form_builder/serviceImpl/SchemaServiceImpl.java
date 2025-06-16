package com.zoho.form_builder.serviceImpl;

import com.zoho.form_builder.modal.Schema;
import com.zoho.form_builder.repository.SchemaRepository;
import com.zoho.form_builder.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaServiceImpl implements SchemaService {
    @Autowired
    private SchemaRepository schemaRepository;
    @Override
    public Schema getSchemaByName(String name) throws Exception {
        Schema schema = schemaRepository.findByName(name);
        if(schema == null){
            throw new Exception("Schema not found with name: "+name);
        }
        return schema;
    }

    @Override
    public Schema storeSchema(Schema schema) throws Exception {
        Schema schema1 = schemaRepository.findByName(schema.getName());
        if(schema1 != null) {
            throw new Exception("schema already found with name: " + schema.getName());
        }
        return schemaRepository.save(schema);
      
    }

    @Override
    public String deleteById(Long id) {
        schemaRepository.deleteById(id);
        return "deleted schema";
    }

    public List<Schema> findAllSchema(){
        return schemaRepository.findAll();
    }

}
