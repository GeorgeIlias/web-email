package george.javawebemail.Utilities;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class BeanJsonTransformer {

    public static <T extends Object> String singleObjectToJsonStringWithFilters(T objectToTurnToJson, String filterName,
            HashSet<String> listOfFilterProperties) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SimpleFilterProvider sfp = new SimpleFilterProvider();
            sfp.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(listOfFilterProperties));
            objMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
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
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SimpleFilterProvider sfp = new SimpleFilterProvider();
            for (Entry<String, HashSet<String>> item : filterHashAndMap.entrySet()) {
                sfp.addFilter(item.getKey(), SimpleBeanPropertyFilter.filterOutAllExcept(item.getValue()));
            }
            objMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
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
            String subbedJsonToReturn = jsonToReturn.substring(0, jsonToReturn.length() - 2);

            System.out.println(subbedJsonToReturn);
            return subbedJsonToReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to make a list of objects into string versions of themselves so that
     * they can be sent to the user with a list of hashSet<String> of filters and
     * properties
     * 
     * @author gIlias
     * @param <T>
     * @param listOfObjectsToReturn
     * @param HashMapSetOfProperties
     */
    public static <T extends Object> String listMultipleObjectToJsonStringWithMultipleFilters(
            List<T> listOfObjectsToReturn, HashMap<String, HashSet<String>> HashMapSetOfProperties) {
        try {
            String jsonToReturn = "";
            for (T currentObjectFromList : listOfObjectsToReturn) {
                jsonToReturn += BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(currentObjectFromList,
                        HashMapSetOfProperties);
                jsonToReturn += ",";
            }
            String subbedJsonToReturn = jsonToReturn.substring(0, jsonToReturn.length() - 2);
            System.out.println(subbedJsonToReturn);
            return subbedJsonToReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}