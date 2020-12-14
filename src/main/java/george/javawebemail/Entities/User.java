package george.javawebemail.Entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.tomcat.util.json.ParseException;

import lombok.Data;

@Data
@Entity
@JsonFilter("userFilter")
@Table(name = "users")
public class User {

    @Column(name = "user_name", unique = true)
    private String userName;

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
    private Date dateOfBirth;

    @Column
    @OneToMany(mappedBy = "userEmailAccounts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmailAccount> userEmailAccounts;

    @Column
    @OneToMany(mappedBy = "userSent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> listOfEmails;

    @Column
    private String cookieHash;

    public User(Long id, String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String userName, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts,
            List<Email> listOfGivenEmails, String cookieHash) {
        this.setId(id);
        this.setUserName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.listOfEmails = listOfGivenEmails;
        this.userEmailAccounts = listOfGivenEmailAccounts;
        this.cookieHash = cookieHash;
    }

    public User(Long id, String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String userName, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts, String cookieHash) {
        this.setId(id);
        this.setUserName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.userEmailAccounts = listOfGivenEmailAccounts;
        this.cookieHash = cookieHash;
    }

    public User(String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String userName, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts, String cookieHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.userEmailAccounts = listOfGivenEmailAccounts;
        this.cookieHash = cookieHash;
        this.setUserName(userName);

    }

    public User(String firstName, String lastName, Date createdAt, long portChosen, String passwordHash,
            String userName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.setUserName(userName);
        this.listOfEmails = new ArrayList<Email>();
        this.userEmailAccounts = new ArrayList<EmailAccount>();

    }

    public User(String firstName, String lastName, String createdAt, long portChosen, String passwordHash,
            String userName, String dateOfBirth) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.createdAt = sdf.parse(createdAt);
            this.dateOfBirth = sdf.parse(dateOfBirth);

            this.firstName = firstName;
            this.lastName = lastName;
            this.portChosen = portChosen;
            this.passwordHash = passwordHash;
            this.setUserName(userName);
            this.listOfEmails = new ArrayList<Email>();
            this.userEmailAccounts = new ArrayList<EmailAccount>();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public User(Long id, String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String userName, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts,
            List<Email> listOfGivenEmails) {
        this.setId(id);
        this.setUserName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.listOfEmails = listOfGivenEmails;
        this.userEmailAccounts = listOfGivenEmailAccounts;
    }

    public User() {
        this.listOfEmails = new ArrayList<Email>();
        this.userEmailAccounts = new ArrayList<EmailAccount>();
    }

    /**
     * setter for date of birth taken from json parameters (from the api)
     * 
     * @author gIlias
     * @param dateOfBirthString
     * @throws ParseException
     */
    public void setDateOfBirth(String dateOfBirthString) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.dateOfBirth = sdf.parse(dateOfBirthString);
        } catch (Exception e) {
            throw new ParseException("The date of birth was improperly entered");
        }
    }

    /**
     * Setter for the created at date taken from the json parameters (from the api)
     * 
     * @author gIlias
     */
    public void setCreatedAt(String createdAtString) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.createdAt = sdf.parse(createdAtString);
        } catch (Exception e) {
            throw new ParseException("The created at date was improperly entered");
        }

    }
}
