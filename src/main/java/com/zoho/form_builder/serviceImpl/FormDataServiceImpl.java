package com.zoho.form_builder.serviceImpl;

import com.zoho.form_builder.modal.FormData;
import com.zoho.form_builder.modal.Schema;
import com.zoho.form_builder.repository.FormDataRepository;
import com.zoho.form_builder.service.FormDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FormDataServiceImpl implements FormDataService {
    @Autowired
    private FormDataRepository formDataRepository;
    @Override
    public List<FormData> getFormDataBySchema(Schema schema) {
        List<FormData> formData = formDataRepository.findBySchema(schema);
        return formData;
    }

    @Override
    public FormData storeFormData(FormData formData) throws Exception {
        Map<String, Map<String, String>> inputSchema = formData.getSchema().getProperties();
        List<String> requiredFields = formData.getSchema().getRequired();

        Map<String, String> fieldsWithValues = formData.getFieldWithValues();

        for(String required: requiredFields){
            if(fieldsWithValues.get(required) == null){
                throw new Exception("the field "+required+" is required");
            }
        }

        for(Map.Entry entry: inputSchema.entrySet()){
            String field = (String) entry.getKey();
            Map<String, String> constraints = (Map<String, String>) entry.getValue();
            // value provided in the form for the input field
            String value = fieldsWithValues.get(field);

            if(constraints.containsKey("type")){
                typeValidator(constraints.get("type"), value);
            }

            if(constraints.containsKey("min") && value != null){
                minValidator(constraints.get("min"), value);
            }

            if(constraints.containsKey("max") && value != null){
                maxValidator(constraints.get("max"), value);
            }

        }
        return formDataRepository.insert(formData);
    }

    private void minValidator(String min, String value) throws Exception {
        int minimum = Integer.parseInt(min);
        if(value.length() < minimum){
            throw new Exception("minimum length required is"+minimum);
        }
    }
    private void maxValidator(String max, String value) throws Exception {
        int maximum = Integer.parseInt(max);
        if(value.length() > maximum){
            throw new Exception("maximum length  is"+maximum);
        }
    }

    private void typeValidator(String type, String value)throws Exception{
        if(value != null && type.equalsIgnoreCase("number") || type.equalsIgnoreCase("integer")){
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

    @Override
    public FormData getFormDataById(String id) throws Exception {
        Optional<FormData> formData = formDataRepository.findById(id);
        if(formData.isEmpty()){
            throw new Exception("form data not found");
        }
        return formData.get();
    }

    @Override
    public String deleteFormDataById(String id) {
        formDataRepository.deleteById(id);
        return "form is deleted successfully";
    }
}
