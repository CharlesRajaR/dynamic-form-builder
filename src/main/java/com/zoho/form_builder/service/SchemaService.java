package com.zoho.form_builder.serviceImpl;

import com.zoho.form_builder.modal.Schema;

public interface SchemaServiceImpl{
    public Schema getSchemaByName(String name) throws Exception;
    public Schema storeSchema(Schema schema) throws Exception;
    public String deleteById(Long id);
}
