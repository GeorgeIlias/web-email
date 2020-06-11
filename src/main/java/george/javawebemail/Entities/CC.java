package george.javawebemail.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;


import lombok.Data;

@Data
@Entity
@Table(name = "cc")
@JsonFilter("CCFilter")
public class CC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ccReceiver;

    @ManyToOne
    @JoinColumn(name = "ccEmailsTable")
    private Email ccEmails;

    public CC(String ccReceiver, Email ccEmails) {
        this.ccEmails = ccEmails;
        this.ccReceiver = ccReceiver;
    }

    public CC(Long id, String ccReceiver, Email ccEmails) {
        this.id = id;
        this.ccReceiver = ccReceiver;
        this.ccEmails = ccEmails;
    }

    public CC() {

    }
}