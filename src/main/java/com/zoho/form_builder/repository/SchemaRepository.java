package com.zoho.form_builder.repository;


import com.zoho.form_builder.modal.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaRepository extends MongoRepository<Schema, String> {
     boolean existsByName(String name);
     public Schema findByName(String name);
     public List<Schema> findAll();
     public void deleteById(Long id);
}
