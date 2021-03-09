/**
 * Class to receive an email, map it to an object and send it back to the client
 * 
 * 
 * **needs testing***
 * 
 * 
 * 
 * @author gIlias
 */
package george.javawebemail.SendingReceiving;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import george.javawebemail.Entities.Attachment;
import george.javawebemail.Entities.BCC;
import george.javawebemail.Entities.CC;
import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.EmailAccount;
import george.javawebemail.Entities.Receivers;
import george.javawebemail.Entities.Senders;
import george.javawebemail.Entities.User;
import george.javawebemail.Exceptions.StoreConnectionError;

public class Receive {

    /**
     * Method to login and get the emails from the imap server using specifically a
     * gmail account
     * 
     *
     * 
     * 
     * @author gIlias
     * @param accountToUse
     * @return
     */
    /*
     * Others GMail folders : [Gmail]/All Mail This folder contains all of your
     * Gmail messages. [Gmail]/Drafts Your drafts. [Gmail]/Sent Mail Messages you
     * sent to other people. [Gmail]/Spam Messages marked as spam. [Gmail]/Starred
     * Starred messages. [Gmail]/Trash Messages deleted from Gmail. Inbox | Inbox
     */
    public static List<Email> getEmailsFromInboxFolder(EmailAccount accountToUse, String folderToReturn)
            throws MessagingException {
        try {
            Properties props = getPropertiesFromPropertiesFile();
            User userToUseFor = accountToUse.getUserEmailAccounts();
            if (LogingInUser.connectToStore(userToUseFor, props, accountToUse)) {

                Folder folderToGetEmailsFrom = LogingInUser.storeToUse.getFolder(folderToReturn);
                folderToGetEmailsFrom.open(Folder.READ_WRITE);
                Message[] messagesToMakeEmailsOutOf = folderToGetEmailsFrom.getMessages();
                List<Email> returningEmail = new ArrayList<>();
                for (Message currentMessage : messagesToMakeEmailsOutOf) {
                    Email emailToAdd = new Email();
                    emailToAdd = setUpFrom(currentMessage.getFrom(), emailToAdd);
                    emailToAdd = setUpToLists(currentMessage.getAllRecipients(), emailToAdd);
                    emailToAdd = setUpContent(currentMessage, emailToAdd);
                    returningEmail.add(emailToAdd);
                }

                if (LogingInUser.closeStore()) {
                    return returningEmail;
                } else {
                    throw new StoreConnectionError("Error when closing the store");
                }
            } else {
                throw new StoreConnectionError("Error when connecting to the store");
            }
        } catch (StoreConnectionError sce) {
            sce.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * method to set up the cc/bcc/receivers from the message to the beans created
     * for this app Takes an already existing email then adding cc/bcc/receivers to
     * it returns the changed email
     * 
     * @author gIlias
     * @param listOfAddresses
     * @param emailToChange
     */
    private static Email setUpToLists(Address[] listOfAddresses, Email emailToChange) {
        List<CC> ccList = new ArrayList<>();
        List<BCC> bccList = new ArrayList<>();
        List<Receivers> receiversList = new ArrayList<>();

        for (Address currentAddress : listOfAddresses) {
            InternetAddress currentInternetAddress = (InternetAddress) currentAddress;
            switch (currentAddress.getType().toLowerCase()) {
                case "bcc":
                    BCC addingBcc = new BCC();
                    addingBcc.setBccReceiver(currentInternetAddress.getAddress());
                    bccList.add(addingBcc);
                    break;
                case "cc":
                    CC addingCC = new CC();
                    addingCC.setCcReceiver(currentInternetAddress.getAddress());
                    ccList.add(addingCC);
                    break;
                case "to":
                    Receivers addingReceivers = new Receivers();
                    addingReceivers.setReceiver(currentInternetAddress.getAddress());
                    receiversList.add(addingReceivers);
                    break;
            }
        }
        emailToChange.setBccEmailsList(bccList);
        emailToChange.setCcEmailsList(ccList);
        emailToChange.setReceiversList(receiversList);
        return emailToChange;
    }

    /**
     * Method to change the
     * 
     * @param fromAddress
     * @param emailToChange
     * @return
     */
    private static Email setUpFrom(Address[] fromAddress, Email emailToChange) {
        List<Senders> listOfSenders = new ArrayList<Senders>();
        for (Address currentFromAddress : fromAddress) {
            InternetAddress currentInternetAddress = (InternetAddress) currentFromAddress;
            Senders senderToAdd = new Senders();
            senderToAdd.setSenderReceiver(currentInternetAddress.getAddress());
            listOfSenders.add(senderToAdd);
        }
        emailToChange.setSender(listOfSenders);
        return emailToChange;
    }

    /**
     * method to setup the "content" of the message into the objects that i set up
     * for it in my beans
     * 
     * @author gIlias
     * @param messageToChange
     * @param emailToChange
     * @return Email
     */
    private static Email setUpContent(Message messageToChange, Email emailToChange)
            throws IOException, MessagingException, Exception {
        List<Attachment> attachmentsToAddToEmail = new ArrayList<>();
        MimeMessageParser parserToUse = new MimeMessageParser((MimeMessage) messageToChange).parse();
        for (DataSource currentAttachment : parserToUse.getAttachmentList()) {
            Attachment attachmentToSet = new Attachment();
            attachmentToSet.setAttachment(currentAttachment.getInputStream().readAllBytes());
            attachmentToSet.setName(currentAttachment.getName());
            attachmentsToAddToEmail.add(attachmentToSet);
        }
        emailToChange.setText(parserToUse.getPlainContent());
        emailToChange.setHtml(parserToUse.getHtmlContent());
        emailToChange.setAttachmentList(attachmentsToAddToEmail);

        return emailToChange;
    }

    /**
     * private method to get the properties from the properties.xml file that will
     * be created
     * 
     * @author gilias
     * @return Properties
     */
    private static Properties getPropertiesFromPropertiesFile() {
        Properties props = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("emailReceivers.properties");
            props.load(fis);
            return props;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
