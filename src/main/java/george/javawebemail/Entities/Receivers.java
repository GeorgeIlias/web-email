/**
 * Entities class for the list of emails that will be receiving this email.
 * @author gIlias
 */
package george.javawebemail.Entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import java.util.HashMap;

import lombok.Data;

@Data
@Entity
@Table(name = "receivers")
@JsonFilter(value = "receiversFilter")
@Component
public class Receivers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String receiver;

    @ManyToOne
    @JoinColumn(name = "emailsTable")
    private Email emails;

    public Receivers(Long id, String receiver, Email emails) {
        this.id = id;
        this.receiver = receiver;
        this.emails = emails;
    }

    public Receivers(String receiver, Email emails) {
        this.receiver = receiver;
        this.emails = emails;
    }

    public Receivers() {
    }

    public void fromMapToObject(HashMap<String, Object> propertyMap) {
        this.id = Long.parseLong(propertyMap.getOrDefault("id", null).toString());
        this.receiver = propertyMap.getOrDefault("receiver", null).toString();
        this.emails = new ObjectMapper().convertValue(
                new Gson().toJson(propertyMap.getOrDefault(("listOfEmails"), null)), new TypeReference<Email>() {
                });
    }

}