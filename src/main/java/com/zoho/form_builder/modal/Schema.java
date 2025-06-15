package com.zoho.form_builder.modal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;

@Document(collection = "schemas")
@Getter
@Setter
public class Schema {
    @MongoId
    private Long id;
    @Indexed(unique = true)
    private String name;
    private Map<String, Map<String, String>> inputFields;
}
