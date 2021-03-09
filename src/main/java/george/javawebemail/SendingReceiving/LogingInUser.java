
package george.javawebemail.SendingReceiving;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.HttpSession;

import george.javawebemail.Entities.EmailAccount;
import george.javawebemail.Entities.User;

/**
 * static imap login to use email service (designed to work with gmail for now)
 * 
 * @author gIlias
 */

public class LogingInUser {

    public static Store storeToUse = null;

    public static void checkStore(Properties propertiesToStore, EmailAccount accountToUse) {
        try {
            if (LogingInUser.storeToUse == null || !LogingInUser.storeToUse.isConnected()) {
                propertiesToStore.setProperty("imap.mail.port",
                        accountToUse.getUserEmailAccounts().getPortChosen().toString());
                LogingInUser.storeToUse = Session.getDefaultInstance(propertiesToStore, null).getStore();
            }
        } catch (Exception e) {
            System.err.println("");
            e.printStackTrace();

        }

    }

    public static boolean closeStore() {
        try {
            if (LogingInUser.storeToUse != null && LogingInUser.storeToUse.isConnected()) {
                LogingInUser.storeToUse.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * Method to find the most things needed for a store to connect and be created
     * 
     * 
     * @author gIlias
     * @param userToConnectWith
     * @param propertiesToStore
     * @param accountToUse
     * @return
     */
    public static boolean connectToStore(User userToConnectWith, Properties propertiesToStore,
            EmailAccount accountToUse) {
        try {
            if (LogingInUser.storeToUse == null) {
                LogingInUser.checkStore(propertiesToStore, accountToUse);
            }
            if (!LogingInUser.storeToUse.isConnected()) {
                LogingInUser.storeToUse.connect("imap.gmail.com",
                        Integer.parseInt(userToConnectWith.getPortChosen().toString()), userToConnectWith.getUserName(),
                        userToConnectWith.getPasswordHash());
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
