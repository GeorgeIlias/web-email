/**
 * class to hold all the constants that will be used to remove certain fields when you return an entity object for the front end
 * @author gIlias
 */
package george.javawebemail.ConstantFilters;

import java.util.HashSet;
import java.util.Arrays;

//FOR OTHER USES CHECK THE LINKS BELOW
//https://www.concretepage.com/jackson-api/jackson-jsonfilter-example#SimpleBeanPropertyFilter
//https://helpx.adobe.com/experience-manager/6-4/sites/developing/using/reference-materials/javadoc/com/fasterxml/jackson/databind/ser/impl/SimpleBeanPropertyFilter.html#serializeAllExcept-java.util.Set-
//https://helpx.adobe.com/experience-manager/6-4/sites/developing/using/reference-materials/javadoc/com/fasterxml/jackson/databind/ser/impl/SimpleBeanPropertyFilter.html
public class JsonFilterConstants {
        // arrays/sets of the attachment properties that are required for many of the to
        // be created controller methods.
        public final static HashSet<String> ATTACHMENT_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "attachment", "attached"));
        public final static HashSet<String> ATTACHMENT_REQUIRED_PROPERTIES = new HashSet<String>(
                        Arrays.asList("attachment", "attached"));
        public final static HashSet<String> ATTACHMENT_OPTIONAL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "attachment", "attached"));
        // arrays/sets of the bcc properties that are required for many of the to be
        // created controller methods.
        public final static HashSet<String> BCC_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "bccEmails", "bccReceiver"));
        public final static HashSet<String> BCC_REQUIRED_PROPERTIES = new HashSet<String>(
                        Arrays.asList("bccEmails", "bccReceiver"));
        public final static HashSet<String> BCC_OPTIONAL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "bccEmails", "bccReceiver"));

        // arrays/sets of the cc properties that are required for many of the to be
        // created controller methods
        public final static HashSet<String> CC_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "ccReceiver", "ccEmails"));
        public final static HashSet<String> CC_REQUIRED_PROPERTIES = new HashSet<String>(
                        Arrays.asList("ccReceiver", "ccEmails"));
        public final static HashSet<String> CC_OPTIONAL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "ccReceiver", "ccEmails"));

        // arrays/sets of the email propeties that are required for many of the to be
        // created controller methods
        public final static HashSet<String> EMAIL_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "sender", "subject", "text", "receiversList", "ccEmailsList",
                                        "bccEmailsList", "attachmentList", "userSent"));
        public final static HashSet<String> EMAIL_REQUIRED_PROPERTIES = new HashSet<String>(
                        Arrays.asList("sender", "subject", "text", "receiversList", "ccEmailsList", "bccEmailsList",
                                        "attachmentList", "userSent"));
        // only contains the optional fields of your entity
        public final static HashSet<String> EMAIL_OPTIONAL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "ccEmailsList", "bccEmailsList", "attachmnetList"));

        // arrays/sets of the receivers properties that are required for many of the to
        // be created controller methods
        public final static HashSet<String> RECEIVERS_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "emails", "receiver"));
        public final static HashSet<String> RECEIVERS_REQUIRED_PROPERTIES = new HashSet<String>(
                        Arrays.asList("emails", "receiver"));
        public final static HashSet<String> RECEIVERS_OPTIONAL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "emails", "receivers"));

        // arrays/sets of the user properties that are required for many of the to be
        // created controller methods
        public final static HashSet<String> USERS_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "firstName", "lastName", "createdAt", "portChosen", "passwordHash",
                                        "userName", "id", "dateOfBirth", "listOfEmails"));
        public final static HashSet<String> USERS_REQUIRED_PROPERTIES = new HashSet<String>(
                        Arrays.asList("firstName", "lastName", "createdAd", "portChosen", "passwordHash", "userName",
                                        "id", "dateOfBirth", "listOfEmails"));
        // Set that contains the optional properties of the current user entity
        public final static HashSet<String> USERS_OPTIONAL_PROPERTIES = new HashSet<String>(Arrays.asList("id"));

        public final static HashSet<String> USERS_OPTIONAL_LOGIN_PROPERTIES = new HashSet<String>(Arrays.asList("id",
                        "firstName", "lastName", "createAd", "userName", "id", "dateOfBirth", "portChosen"));

        // Arrays/ sets of the user properties that are required for many of the to be
        // created controller methods
        public final static HashSet<String> EMAILACCOUNTS_ALL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("id", "emailAccount", "emailPasswordHash", "userGiven"));

        public final static HashSet<String> EMAILACCOUNTS_OPTIONAL_PROPERTIES = new HashSet<String>(
                        Arrays.asList("emailAccount", "emailPasswordHash", "userGiven"));

        // Arrays/sets of the user's embedded id
        public final static HashSet<String> USER_EMBEDDED = new HashSet<String>(Arrays.asList("id", "userName"));
}
