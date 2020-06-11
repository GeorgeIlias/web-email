/**
 * Entity class to create the table for attachments that will be brought over when the user logs in and sends or receives an email.
 * @author gIlias
 */

package george.javawebemail.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;


import lombok.Data;

@Data
@Entity
@Table(name = "Attachment")
@JsonFilter("attachmentFilter")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "attachment", nullable = false, columnDefinition = "BLOB")
    private byte[] attachment;

    @ManyToOne
    @JoinColumn(name = "attachedTable")
    private Email attached;

    public Attachment(byte[] attachment, Email attached) {
        this.attached = attached;
        this.attachment = attachment;
    }

    public Attachment(Long id, byte[] attachment, Email attached) {
        this.id = id;
        this.attached = attached;
        this.attachment = attachment;
    }

    public Attachment() {

    }
}