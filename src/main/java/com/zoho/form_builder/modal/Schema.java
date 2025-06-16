package com.zoho.form_builder.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;

@Document(collection = "schemas")
@Data
public class Schema {
    @MongoId
    private String id;
    @Indexed(unique = true)
    private String name;
    private Map<String, Map<String, String>> properties;
    private List<String> required;
}
