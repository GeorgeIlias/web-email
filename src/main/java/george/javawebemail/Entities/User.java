package george.javawebemail.Entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import george.javawebemail.Utilities.PlainTextToHashUtil;
import lombok.Data;

@Data
@Entity
@JsonFilter("userFilter")
@Table(name = "users")
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Date createdAt;

    @Column
    private Long portChosen;

    @Column
    private String passwordHash;

    @Column
    private String username;

    @Column
    private Date dateOfBirth;

    @Column
    @OneToMany(mappedBy = "userEmailAccounts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmailAccount> userEmailAccounts;

    @Column
    @OneToMany(mappedBy = "userSent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> listOfEmails;

    public User(Long id, String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String username, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts,
            List<Email> listOfGivenEmails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.listOfEmails = listOfGivenEmails;
        this.userEmailAccounts = listOfGivenEmailAccounts;
    }

    public User(Long id, String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String username, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.userEmailAccounts = listOfGivenEmailAccounts;
    }

    public User(String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String username, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.userEmailAccounts = listOfGivenEmailAccounts;

    }

    public User(String firstName, String lastName, Date createdAt, long portChosen, String passwordHash,
            String username, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.username = username;
        this.dateOfBirth = dateOfBirth;

    }

    public User() {
    }

    /**
     * need to complete this method to convert a map of string,object
     * 
     * @param propertyMap
     * @author gIlias
     */
    public void fromMapToObject(HashMap<String, Object> propertyMap) {
        try {
            this.id = Long.parseLong(propertyMap.getOrDefault("id", null).toString());
            SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
            this.createdAt = sdf.parse(propertyMap.get("createdAt").toString());
            this.username = propertyMap.getOrDefault("username", null).toString();
            this.firstName = propertyMap.getOrDefault("firstName", null).toString();
            this.lastName = propertyMap.getOrDefault("lastName", null).toString();
            this.portChosen = new Long(propertyMap.getOrDefault("portChosen", "25").toString());
            this.listOfEmails = new ObjectMapper().convertValue(
                    new Gson().toJson(propertyMap.getOrDefault("listOfEmails", null)),
                    new TypeReference<List<Email>>() {
                    });
            this.id = new Long(propertyMap.getOrDefault("id", null).toString());
            this.passwordHash = PlainTextToHashUtil.addSaltAndConvert(propertyMap.get("passwordUnHash").toString());
            this.dateOfBirth = sdf.parse(propertyMap.get("dateOfBirth").toString());
            this.userEmailAccounts = new ObjectMapper().convertValue(
                    new Gson().toJson(propertyMap.getOrDefault("userEmailAccounts", null).toString()),
                    new TypeReference<List<EmailAccount>>() {
                    });
        } catch (ParseException a) {
            a.printStackTrace();
        } catch (Exception b) {
            b.printStackTrace();
        }

    }
}
