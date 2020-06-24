package george.javawebemail.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;

@Data
@Table(name = "emailAccounts")
@Entity
@JsonFilter(value = "emailAccountFilter")
public class EmailAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String emailAccount;

    @Column
    private String emailPasswordHash;

    @ManyToOne
    @JoinColumn(name = "userEmailAccountsId", referencedColumnName = "id")
    private User userEmailAccounts;

    public EmailAccount(Long id, String userPasswordHash, String emailPasswordHash, User userGiven) {
        this.id = id;
        this.emailAccount = userPasswordHash;
        this.emailPasswordHash = emailPasswordHash;
        this.userEmailAccounts = userGiven;

    }

    public EmailAccount() {

    }

    public EmailAccount(String email, String emailPasswordHash) {
        this.emailAccount = email;
        this.emailPasswordHash = emailPasswordHash;
    }

    public void fromMapToObject(HashMap<String, Object> propertyMap) {
        this.id = Long.parseLong(propertyMap.getOrDefault("id", null).toString());
        this.emailAccount = propertyMap.getOrDefault("emailAccount", null).toString();
        this.emailPasswordHash = propertyMap.getOrDefault("emailPasswordHash", null).toString();

    }
}