package george.javawebemail.Entities;
/*
* Entity class for the bcc email list that an email might possibly have.
 * @author gIlias
 */

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;

@Data
@Entity
@Table(name = "bcc")
@JsonFilter("BCCFilter")
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
}