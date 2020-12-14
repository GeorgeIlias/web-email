/**
 * Entity class to create the table for attachments that will be brought over when the user logs in and sends or receives an email.
 * @author gIlias
 */

package george.javawebemail.Entities;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.Data;

@Data
@Entity
@Table(name = "Attachment")
@JsonFilter("attachmentFilter")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "attachment", nullable = false, columnDefinition = "BLOB")
    private byte[] attachment;

    @ManyToOne
    @JoinColumn(name = "attachedTable")
    private Email attached;

    public Attachment(byte[] attachment, Email attached) {
        this.attached = attached;
        this.attachment = attachment;
    }

    public Attachment(Long id, byte[] attachment, Email attached) {
        this.id = id;
        this.attached = attached;
        this.attachment = attachment;
    }

    public Attachment() {

    }

    public void setAttachment(String attachmentString) {
        char[] attachmentCharArray = attachmentString.toCharArray();
        this.attachment = new byte[attachmentCharArray.length];
        for (int counter = 0; counter < attachmentCharArray.length; counter++) {
            this.attachment[counter] = (byte) attachmentCharArray[counter];
        }
    }

    public void setAttachment(byte[] attachmentByte) {
        this.attachment = attachmentByte;
    }

    /**
     * Method to set a HashMap into an email and then set it into the Attachment
     * object
     * 
     * 
     * @param attachedEmailMap
     */
    public void setAttached(HashMap<String, String> attachedEmailMap) {
        try {
            Email attachedEmail = new ObjectMapper().readValue(new Gson().toJson(attachedEmailMap), Email.class);
            this.setAttached(attachedEmail);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

    }

    public void setAttached(Email attached) {
        this.attached = attached;
    }
}