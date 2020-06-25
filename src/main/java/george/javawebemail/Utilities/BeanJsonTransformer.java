package george.javawebemail.Utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
            return objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectsToTurnToJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to make a list of objects into string versions of them so that they
     * can be sent to the user
     * 
     * @author gIlias
     * @param <T>
     * @param listOfObjectsToReturn
     * @param filterName
     * @param listOfFilterProperties
     * @return Object
     */
    public static <T extends Object> String ListSingleObjectToJsonStringWithFilters(List<T> listOfObjectsToReturn,
            String filterName, HashSet<String> listOfFilterProperties) {
        try {
            String jsonToReturn = "";
            for (T currentObjectToReturn : listOfObjectsToReturn) {
                jsonToReturn += singleObjectToJsonStringWithFilters(currentObjectToReturn, filterName,
                        listOfFilterProperties) + ",";
            }
            jsonToReturn.substring(0, jsonToReturn.length() - 2);

            System.out.println(jsonToReturn);
            return jsonToReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}