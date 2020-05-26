package george.javawebemail.Entities;
/*
* Entity class for the bcc email list that an email might possibly have.
 * @author gIlias
 */

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import lombok.Data;
import java.util.HashMap;

@Data
@Entity
@Table(name = "bcc")
@JsonFilter("BCCFilter")
@Component
public class BCC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bccReceiver;

    @ManyToOne
    @JoinColumn(name = "bccEmailsTable")
    private Email bccEmails;

    public BCC(String bccReceiver, Email bccEmails) {
        this.bccEmails = bccEmails;
        this.bccReceiver = bccReceiver;

    }

    public BCC(Long id, String bccReceiver, Email bccEmails) {
        this.id = id;
        this.bccReceiver = bccReceiver;
        this.bccEmails = bccEmails;
    }

    public BCC() {
    }

    public void fromHashToMap(HashMap<String, Object> propertyMap) {
        this.id = Long.parseLong(propertyMap.getOrDefault("id", null).toString());
        this.bccReceiver = propertyMap.getOrDefault("bccReceiver", null).toString();
        this.bccEmails = new ObjectMapper().convertValue(new Gson().toJson(propertyMap.getOrDefault("bccEmail", null)),
                new TypeReference<Email>() {
                });
    }
}