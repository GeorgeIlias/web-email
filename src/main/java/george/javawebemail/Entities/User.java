package george.javawebemail.Entities;

import java.io.Serializable;
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
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

import george.javawebemail.Entities.EmbeddableID.UserEmbeddableId;
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

    public User(Long id, String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String userName, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts) {
        this.setId(id);
        this.setUserName(userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.userEmailAccounts = listOfGivenEmailAccounts;
    }

    public User(String firstName, String lastName, Date createdAt, Long portChosen, String passwordHash,
            String userName, Date dateOfBirth, List<EmailAccount> listOfGivenEmailAccounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.portChosen = portChosen;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.userEmailAccounts = listOfGivenEmailAccounts;
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

    public User() {
        this.listOfEmails = new ArrayList<Email>();
        this.userEmailAccounts = new ArrayList<EmailAccount>();
    }
}
