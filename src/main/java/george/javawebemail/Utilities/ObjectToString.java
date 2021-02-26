package george.javawebemail.Utilities;

import george.javawebemail.Entities.User;

public class ObjectToString {

    /**
     * method to get all the methods for the user and then retuning the string
     * 
     * @author gIlias
     */
    public static String getUserStringForStorage(User userToSend) {
        String userString = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(userToSend,
                PropertyReturnTypesForControllers.UserControllerProperties.returnNewUserProperties());
        return userString;

    }

    // TODO ADD THE OTHER OBJECT TO STRINGS FOR FUTURE USE

}
