//package com.OlympusRiviera;
//
//import com.OlympusRiviera.model.Plan.PlanDetails;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.util.List;
//
//@Converter
//public class JsonListConverter implements AttributeConverter<List<PlanDetails>, String> {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(List<PlanDetails> attribute) {
//        if (attribute == null) {
//            return null;
//        }
//        try {
//            // Convert the list to a JSON string representation (this is correct)
//            return objectMapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error converting list to JSON string", e);
//        }
//    }
//
//    @Override
//    public List<PlanDetails> convertToEntityAttribute(String dbData) {
//        if (dbData == null || dbData.isEmpty()) {
//            return null;
//        }
//        try {
//            // Convert the JSON string back to a list of PlanDetails
//            return objectMapper.readValue(dbData,
//                    objectMapper.getTypeFactory().constructCollectionType(List.class, PlanDetails.class));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error converting JSON string to list", e);
//        }
//    }
//}
