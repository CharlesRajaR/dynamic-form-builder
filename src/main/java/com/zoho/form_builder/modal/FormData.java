package com.zoho.form_builder.modal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
//import org.springframework.data.annotation.Id;

import java.util.Map;

@Document(collection = "formSubmissions")
@Getter
@Setter
public class FormData {
    @MongoId
    private Long id;
    @DBRef
    private Schema schema;
    private Map<String, String> fieldWithValues;
}
