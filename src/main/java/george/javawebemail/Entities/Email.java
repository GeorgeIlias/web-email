/**
 * Entity class for the table made up for emails sent and received through this website/service
 * 
 * @author gIlias
 */
package george.javawebemail.Entities;

import java.util.HashMap;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Entity
@Table(name = "emails")
@JsonFilter("emailFilter")
@Component
public class Email {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String sender;

        @Column
        private String subject;

        @Lob
        @Column(name = "textBody")
        @Type(type = "text")
        private String text;

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
        @JoinColumn(name = "emailUserTable")
        private User userSent;

        public Email(Long id, String sender, String subject, List<Receivers> receiverList, List<CC> ccEmailsList,
                        List<BCC> bccEmailsList, List<Attachment> attachmentList, User user) {
                this.id = id;
                this.sender = sender;
                this.subject = subject;
                this.receiversList = receiverList;
                this.ccEmailsList = ccEmailsList;
                this.bccEmailsList = bccEmailsList;
                this.attachmentList = attachmentList;
                this.userSent = user;
        }

        public Email(String sender, String subject, List<Receivers> receiverList, List<CC> ccEmailsList,
                        List<BCC> bccEmailsList, List<Attachment> attachmentList, User user) {
                this.sender = sender;
                this.subject = subject;
                this.receiversList = receiverList;
                this.ccEmailsList = ccEmailsList;
                this.bccEmailsList = bccEmailsList;
                this.attachmentList = attachmentList;
                this.userSent = user;
        }

        public Email() {
        }

        public void fromMapToObject(HashMap<String, Object> propertyMap) {

                this.attachmentList = new ObjectMapper().convertValue(
                                new Gson().toJson(propertyMap.getOrDefault("attachmentList", null)),
                                new TypeReference<List<Attachment>>() {
                                });
                this.bccEmailsList = new ObjectMapper().convertValue(
                                new Gson().toJson(propertyMap.getOrDefault("bccEmailsList", null)),
                                new TypeReference<List<BCC>>() {
                                });
                this.ccEmailsList = new ObjectMapper().convertValue(
                                new Gson().toJson(propertyMap.getOrDefault("ccEmailsList", null)),
                                new TypeReference<List<CC>>() {
                                });
                this.receiversList = new ObjectMapper().convertValue(
                                new Gson().toJson(propertyMap.getOrDefault("receiversList", null)),
                                new TypeReference<List<Receivers>>() {
                                });
                this.sender = this.subject = propertyMap.getOrDefault("subject", null).toString();
                this.text = propertyMap.getOrDefault("text", null).toString();
                this.userSent = new ObjectMapper().convertValue(
                                new Gson().toJson(propertyMap.getOrDefault("userSent", null)),
                                new TypeReference<User>() {
                                });
        }

}