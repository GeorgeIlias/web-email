/**
 * Entities class for the list of emails that will be receiving this email.
 * @author gIlias
 */
package george.javawebemail.Entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;

@Data
@Entity
@Table(name = "receivers")
@JsonFilter(value = "receiversFilter")
public class Receivers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String receiver;

    @ManyToOne
    @JoinColumn(name = "emailsTable")
    private Email emails;

    public Receivers(Long id, String receiver, Email emails) {
        this.id = id;
        this.receiver = receiver;
        this.emails = emails;
    }

    public Receivers(String receiver, Email emails) {
        this.receiver = receiver;
        this.emails = emails;
    }

    public Receivers() {
    }

}