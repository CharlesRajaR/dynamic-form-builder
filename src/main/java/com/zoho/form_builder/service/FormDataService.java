package com.zoho.form_builder.serviceImpl;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;

import java.text.Normalizer;
import java.util.List;

public interface FormDataService {
    public List<FormData> getFormDataBySchema(Schema schema);
    public FormData storeFormData(FormData formData) throws Exception;
    public FormData getFormDataById(Long id) throws Exception;
    public String deleteFormDataById(Long id);
}
