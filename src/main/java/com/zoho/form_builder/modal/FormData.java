package com.zoho.form_builder.modal;

import lombok.Data;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
//import org.springframework.data.annotation.Id;

import java.util.Map;

@Document(collection = "formSubmissions")
@Data
public class FormData {
    @MongoId
    private String id;
    @DBRef
    private Schema schema;
    private Map<String, String> fieldWithValues;
}
