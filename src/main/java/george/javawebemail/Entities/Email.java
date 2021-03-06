/**
 * Entity class for the table made up for emails sent and received through this website/service
 * 
 * @author gIlias
 */
package george.javawebemail.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;

import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
@Table(name = "emails")
@JsonFilter("emailFilter")
public class Email {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String subject;

        @Lob
        @Column(name = "textBody")
        @Type(type = "text")
        private String text;

        @Lob
        @Column
        @Type(type = "text")
        private String html;

        @Column
        @OneToMany(mappedBy = "senderEmails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Senders> sender;

        @Column
        @OneToMany(mappedBy = "emails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Receivers> receiversList;

        @Column
        @OneToMany(mappedBy = "ccEmails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<CC> ccEmailsList;

        @Column
        @OneToMany(mappedBy = "bccEmails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<BCC> bccEmailsList;

        @Column
        @OneToMany(mappedBy = "attached", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Attachment> attachmentList;

        @ManyToOne
        @JoinColumn(name = "emailUserTableId", referencedColumnName = "id")
        private User userSent;

        public Email(Long id, List<Senders> sender, String subject, List<Receivers> receiverList, List<CC> ccEmailsList,
                        List<BCC> bccEmailsList, List<Attachment> attachmentList, User user, String html) {
                this.id = id;
                this.sender = sender;
                this.subject = subject;
                this.receiversList = receiverList;
                this.ccEmailsList = ccEmailsList;
                this.bccEmailsList = bccEmailsList;
                this.attachmentList = attachmentList;
                this.userSent = user;
                this.html = html;
        }

        public Email(List<Senders> sender, String subject, List<Receivers> receiverList, List<CC> ccEmailsList,
                        List<BCC> bccEmailsList, List<Attachment> attachmentList, User user) {
                this.sender = sender;
                this.subject = subject;
                this.receiversList = receiverList;
                this.ccEmailsList = ccEmailsList;
                this.bccEmailsList = bccEmailsList;
                this.attachmentList = attachmentList;
                this.userSent = user;
        }

        public Email(List<Senders> sender, String subject, List<Receivers> receiverList, List<CC> ccEmailsList,
                        List<BCC> bccEmailsList, User user) {
                this.sender = sender;
                this.subject = subject;
                this.receiversList = receiverList;
                this.ccEmailsList = ccEmailsList;
                this.bccEmailsList = bccEmailsList;
                this.userSent = user;
        }

        public Email() {
        }
}