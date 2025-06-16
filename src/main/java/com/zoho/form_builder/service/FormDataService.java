package com.zoho.form_builder.service;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;

import java.util.List;

public interface FormDataService {
    public List<FormData> getFormDataBySchema(Schema schema);
    public FormData storeFormData(FormData formData) throws Exception;
    public FormData getFormDataById(String id) throws Exception;
    public String deleteFormDataById(String id);
}
