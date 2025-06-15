package com.zoho.form_builder.service;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;
import com.zoho.form_builder.repository.FormDataRepository;
import com.zoho.form_builder.serviceImpl.FormDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FormDataService implements FormDataServiceImpl {
    @Autowired
    private FormDataRepository formDataRepository;
    @Override
    public List<FormData> getFormDataBySchema(Schema schema) {
        List<FormData> formData = formDataRepository.findBySchema(schema);
        return formData;
    }

    @Override
    public FormData storeFormData(FormData formData) throws Exception {
        Map<String, Map<String, String>> inputSchema = formData.getSchema().getInputFields();
        Map<String, String> fieldsWithValues = formData.getFieldWithValues();

        for(Map.Entry entry: inputSchema.entrySet()){
            String field = (String) entry.getKey();
            Map<String, String> constraints = (Map<String, String>) entry.getValue();
            // value provided in the form for the input field
            String value = fieldsWithValues.get(field);

            if(value == null && constraints.get("requried").equals("true")){
                throw new Exception(value+" is not found");
            }

            String type = constraints.get("type");

            if(value != null && type.equalsIgnoreCase("number")){
                try {
                    Integer.parseInt(value);
                }
                catch (NumberFormatException e){
                    throw new Exception("not a valid number");
                }
            }

            else if (value != null && type.equalsIgnoreCase("email")) {
              String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                if (!value.matches(regex)) {
                     throw new Exception("email is not valid");
                }
            }

            else if (value != null && type.equalsIgnoreCase("password")) {
                String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&...])[A-za-z\\d@$!...]{8,50}$";

                if(!value.matches(passwordRegex)){
                    throw new Exception("password is not valid");
                }
            }
            else{
                //default string
            }

        }
        return formDataRepository.insert(formData);
    }

    @Override
    public FormData getFormDataById(Long id) throws Exception {
        Optional<FormData> formData = formDataRepository.findById(id);
        if(formData.isEmpty()){
            throw new Exception("form data not found");
        }
        return formData.get();
    }

    @Override
    public String deleteFormDataById(Long id) {
        formDataRepository.deleteById(id);
        return "form is deleted successfully";
    }
}
