package com.zoho.form_builder.repository;


import com.zoho.form_builder.modal.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SchemaRepository extends MongoRepository<Schema, Long> {
     public Schema findByName(String name);
     public List<Schema> findAll();
      public void deleteById(Long id);
}
