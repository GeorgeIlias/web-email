/**
 * Methods that will return the appropriate hashsets for the controllers in this program
 * They are all in this class to allow for ease of use when changing any of them.
 * 
 * @author gIlias
 */

package george.javawebemail.Utilities;

import java.util.HashMap;
import java.util.HashSet;

import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.ConstantFilters.JsonFilterNameConstants;

public class PropertyReturnTypesForControllers {

    /**
     * Internal class to store and group all the hashmaps together for the
     * EmailController properties
     * 
     * @author gIlias
     */
    public static class EmailControllerProperties {
        // methods for the email controller and entity
        public static HashMap<String, HashSet<String>> returnTypicalPropertiesForEmail() {
            HashMap<String, HashSet<String>> filterHashAndSet = new HashMap<String, HashSet<String>>();
            filterHashAndSet.put(JsonFilterNameConstants.EMAIL_FILTER_NAME,
                    JsonFilterConstants.EMAIL_REQUIRED_PROPERTIES);
            filterHashAndSet.put(JsonFilterNameConstants.USERS_FILTER_NAME,
                    JsonFilterConstants.USERS_OPTIONAL_UNIDENTIFIABLE_PROPERTIES);
            filterHashAndSet.put(JsonFilterNameConstants.CC_FILTER_NAME, JsonFilterConstants.CC_REQUIRED_PROPERTIES);
            filterHashAndSet.put(JsonFilterNameConstants.BCC_FILTER_NAME, JsonFilterConstants.BCC_REQUIRED_PROPERTIES);
            filterHashAndSet.put(JsonFilterNameConstants.RECEIVERS_FILTER_NAME,
                    JsonFilterConstants.RECEIVERS_REQUIRED_PROPERTIES);
            filterHashAndSet.put(JsonFilterNameConstants.ATTACHMENT_FILTER_NAME,
                    JsonFilterConstants.ATTACHMENT_REQUIRED_PROPERTIES);
            filterHashAndSet.put(JsonFilterNameConstants.SENDER_FILTER_NAME,
                    JsonFilterConstants.SENDERS_ALL_PROPERTIES);

            return filterHashAndSet;
        }

    }

    /**
     * internal class to group all the property hashmaps together for the attachment
     * controller properties
     * 
     * @author gIlias
     */
    public static class AttachmentControllerProperties {
        public static HashMap<String, HashSet<String>> returnTypicalPropertiesForAttachment() {
            HashMap<String, HashSet<String>> hashMapSet = new HashMap<String, HashSet<String>>();
            hashMapSet.put("attachmentFilter", new HashSet<String>(JsonFilterConstants.ATTACHMENT_ALL_PROPERTIES));
            hashMapSet.put("emailFilter", new HashSet<String>(JsonFilterConstants.EMAIL_ALL_PROPERTIES));
            return hashMapSet;
        }
    }

    /**
     * email controller class to group all the property hasmaps together for the
     * attachment controller properties
     * 
     * @author gIlias
     */
    public static class EmailAccountControllerProperties {
        // method to return the default/required filters for the given objects
        public static HashMap<String, HashSet<String>> returnTypicalPropertiesForEmailAccount() {
            HashMap<String, HashSet<String>> returningObject = new HashMap<String, HashSet<String>>();
            returningObject.put(JsonFilterNameConstants.USERS_FILTER_NAME, JsonFilterConstants.USERS_ALL_PROPERTIES);
            return returningObject;
        }

        // method to return the optional filters for the given objects
        public static HashMap<String, HashSet<String>> returnOptionalPropertiesForEmailAccount() {
            HashMap<String, HashSet<String>> returningObject = new HashMap<String, HashSet<String>>();
            returningObject.put(JsonFilterNameConstants.USERS_FILTER_NAME,
                    JsonFilterConstants.USERS_OPTIONAL_PROPERTIES);
            return returningObject;
        }
    }

    /**
     * user class to to group all the property hashmaps together for the user
     * controller properties
     */
    public static class UserControllerProperties {
        // method to return user creation
        public static HashMap<String, HashSet<String>> returnNewUserProperties() {
            HashMap<String, HashSet<String>> filterToAdd = new HashMap<String, HashSet<String>>();
            filterToAdd.put(JsonFilterNameConstants.USERS_FILTER_NAME, JsonFilterConstants.USERS_ALL_PROPERTIES);
            filterToAdd.put(JsonFilterNameConstants.EMAIL_ACCOUNT_FILTER_NAME,
                    JsonFilterConstants.EMAILACCOUNTS_ALL_PROPERTIES);
            filterToAdd.put(JsonFilterNameConstants.EMAIL_FILTER_NAME, JsonFilterConstants.EMAIL_ALL_PROPERTIES);
            return filterToAdd;
        }

        // method to get the necesseary filters for the jsonConstants
        public static HashMap<String, HashSet<String>> returnTypicalPropertiesForUser() {
            HashMap<String, HashSet<String>> jsonFilterHashMap = new HashMap<String, HashSet<String>>();
            jsonFilterHashMap.put(JsonFilterNameConstants.USERS_FILTER_NAME,
                    JsonFilterConstants.USERS_REQUIRED_PROPERTIES);
            jsonFilterHashMap.put(JsonFilterNameConstants.EMAIL_ACCOUNT_FILTER_NAME,
                    JsonFilterConstants.EMAILACCOUNTS_ALL_PROPERTIES);
            jsonFilterHashMap.put(JsonFilterNameConstants.EMAIL_FILTER_NAME,
                    JsonFilterConstants.EMAIL_REQUIRED_PROPERTIES);
            jsonFilterHashMap.put(JsonFilterNameConstants.USER_FOLDERS_FILTER_NAME,
                    JsonFilterConstants.USER_FOLDER_REQUIRED_PROPERTIES);
            return jsonFilterHashMap;
        }
    }
}
