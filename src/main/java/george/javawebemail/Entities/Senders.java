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
@Table(name = "senders")
@JsonFilter("senderFilter")

public class Senders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String senderReceiver;

    @ManyToOne
    @JoinColumn(name = "senderEmailsTable")
    private Email senderEmails;

    public Senders(String senderReceiver, Email senderEmails) {
        this.senderReceiver = senderReceiver;
        this.senderEmails = senderEmails;
    }

    public Senders(Long id, String senderReceiver, Email senderEmails) {
        this.id = id;
        this.senderReceiver = senderReceiver;
        this.senderEmails = senderEmails;
    }

    public Senders() {

    }

}
