package george.javawebemail.Utilities;

import java.util.HashMap;
import java.util.HashSet;

import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.ConstantFilters.JsonFilterNameConstants;
import george.javawebemail.Entities.User;

public class ObjectToString {

    /**
     * method to get all the methods for the user and then retuning the string
     * 
     * @author gIlias
     */
    public static String getUserStringForStorage(User userToSend) {

        HashMap<String, HashSet<String>> filterToAdd = new HashMap<String, HashSet<String>>();
        filterToAdd.put(JsonFilterNameConstants.USERS_FILTER_NAME, JsonFilterConstants.USERS_ALL_PROPERTIES);
        filterToAdd.put(JsonFilterNameConstants.EMAIL_ACCOUNT_FILTER_NAME,
                JsonFilterConstants.EMAILACCOUNTS_ALL_PROPERTIES);
        filterToAdd.put(JsonFilterNameConstants.EMAIL_FILTER_NAME, JsonFilterConstants.EMAIL_ALL_PROPERTIES);
        String userString = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(userToSend, filterToAdd);
        return userString;

    }

    // TODO ADD THE OTHER OBJECT TO STRINGS FOR FUTURE USE

}
