package com.zoho.form_builder.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

            if(constraints.containsKey("type") && value != null){
                typeValidator(constraints.get("type"), value);
            }
            
            String type = constraints.get("type");

            if(type == "number" || type == "integer" && constraints.containsKey("min") && value != null){
                minValidator(constraints.get("min"), value);
            }

            if(type == "number" || type == "integer" && constraints.containsKey("max") && value != null){
                maxValidator(constraints.get("max"), value);
            }

            if(type == "date" && constraints.containsKey("min") && value != null){
                minDateValidator(constraints.get("min"), value);
            }

            if(type == "date" && constraints.containsKey("max") && value != null){
                maxDateValidator(constraints.get("max"), value);
            }

            if(constraints.containsKey("minLength") && value != null){
                minLengthValidator(constraints.get("minLength"), value);
            }

            if(constraints.containsKey("maxLength") && value != null){
                maxLengthValidator(constraints.get("maxLength"), value);
            }

        }
        return formDataRepository.insert(formData);
    }
    
    private void minDateValidator(String min, String value) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        Date minDate = sdf2.parse(min);
        Date date = sdf.parse(value);
        if(date.compareTo(minDate) < 0){
            throw new Exception("minimum date must be: "+minDate);
        }
    }
    private void maxDateValidator(String max, String value) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        Date maxDate = sdf2.parse(max);
        Date date = sdf.parse(value);
        if(date.compareTo(maxDate) > 0){
            throw new Exception("maximum date must be: "+maxDate);
        }
    }
    private void minValidator(String min, String value) throws Exception {
        int minimum = Integer.parseInt(min);
        int value1 = Integer.parseInt(value);
        if(value1 < minimum){
            throw new Exception("minimum number must be: "+minimum);
        }
    }
    private void maxValidator(String max, String value) throws Exception {
        int maximum = Integer.parseInt(max);
        int value1 = Integer.parseInt(value);

        if(value1 > maximum){
            throw new Exception("maximum number must be: "+maximum);
        }
    }
    private void minLengthValidator(String min, String value) throws Exception {
        int minimum = Integer.parseInt(min);
        if(value.length() < minimum){
            throw new Exception("minimum length required is"+minimum);
        }
    }
    private void maxLengthValidator(String max, String value) throws Exception {
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
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$";

            if(!value.matches(passwordRegex)){
                throw new Exception("password is not valid");
            }
        }
        else if(value != null && type.equalsIgnoreCase("date")){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false); // Strict parsing

            try{
                Date inputDate = sdf.parse(value);
            }
            catch(ParseException e){
                throw new Exception("date is invalid");
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
