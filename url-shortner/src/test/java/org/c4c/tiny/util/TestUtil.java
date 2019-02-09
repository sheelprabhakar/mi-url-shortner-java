package org.c4c.tiny.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TestUtil {

	public static String convertToJSON(Object object)
    {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return convertToJSON(e);
        }
        return json;
    }


    public static <T> T convertToObject(Class<T> clazz,String jsonString)
    {
        try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, clazz);
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
  
}
