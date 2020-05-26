package george.javawebemail.Utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class BeanJsonTransformer {

    public static <T extends Object> String singleObjectToJsonStringWithFilters(T objectToTurnToJson, String filterName,
            HashSet<String> listOfFilterProperties) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            SimpleFilterProvider sfp = new SimpleFilterProvider();
            sfp.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(listOfFilterProperties));
            objMapper.setFilterProvider(sfp);
            String jsonData = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectToTurnToJson);
            System.out.println(jsonData);
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Object> String multipleObjectsToJsonStringWithFilters(T objectsToTurnToJson,
            HashMap<String, HashSet<String>> filterHashAndMap) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            SimpleFilterProvider sfp = new SimpleFilterProvider();
            for (Entry<String, HashSet<String>> item : filterHashAndMap.entrySet()) {
                sfp.addFilter(item.getKey(), SimpleBeanPropertyFilter.filterOutAllExcept(item.getValue()));
            }
            objMapper.setFilterProvider(sfp);
            String jsonStringData = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectsToTurnToJson);
            System.out.println(jsonStringData);
            return jsonStringData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}