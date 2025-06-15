package com.zoho.form_builder.service;

import com.zoho.form_builder.modal.Schema;

import java.util.List;

public interface SchemaService{
    public Schema getSchemaByName(String name) throws Exception;
    public Schema storeSchema(Schema schema) throws Exception;
    public String deleteById(Long id);
    public List<Schema> findAllSchema();
}
