package com.zoho.form_builder.repository;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FormDataRepository extends MongoRepository<FormData, Long> {
    public List<FormData> findBySchema(Schema schema);
    public Optional<FormData> findById(Long id);
    public void deleteById(Long id);
}
