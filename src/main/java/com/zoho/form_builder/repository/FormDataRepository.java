package com.zoho.form_builder.repository;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDataRepository extends MongoRepository<FormData, String> {
    public List<FormData> findBySchema(Schema schema);
    public Optional<FormData> findById(String id);
    public void deleteById(String id);
}
