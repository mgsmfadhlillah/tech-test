package com.telkomsel.utils;

import com.telkomsel.entity.model.RestResponse;
import com.telkomsel.logger.Debug;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionUtils {
  public static ResponseEntity<Object> translate(Exception ex) throws JsonProcessingException {
    RestResponse response = new RestResponse();
    String json = "";
    switch (ex.getClass().getSimpleName()) {
      case "DataIntegrityViolationException":
      case "SQLIntegrityConstraintViolationException":
        response.setStatus_code(HttpStatus.UNPROCESSABLE_ENTITY);
        response.setStatus_desc(ConstantVariable.DATA_DUPLICATE_DESC);
        break;
      case "NullPointerException":
        response.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setStatus_desc(ConstantVariable.NULL_DESC);
        break;
      default:
        Debug.info("Exception Type : " + ex.getClass().getName());
        response.setStatus_code(HttpStatus.BAD_REQUEST);
        response.setStatus_desc(ConstantVariable.DB_ERROR_DESC);
        break;
    }
    json = new ObjectMapper().writeValueAsString(response);
    Debug.info("Exception Responses : "+json);

    return new ResponseEntity<Object>(json, response.getStatus_code());
  }
  public static ResponseEntity<Object> contentReader(RestResponse response) throws ParseException, JsonProcessingException {
    ResponseEntity<Object> obj = null;
    if(!response.getContent().equals(ConstantVariable.FAILED)){
      org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) new JSONParser().parse(response.getContent());
      obj = new ResponseEntity<Object>(jsonArray, response.getStatus_code());
    }else{
      obj = new ResponseEntity<Object>(new ObjectMapper().writeValueAsString(response), response.getStatus_code());
    }
    return obj;
  }
}
