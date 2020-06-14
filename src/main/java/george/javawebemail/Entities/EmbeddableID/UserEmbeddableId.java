/**
 * Embeddable username id pair for a user account so that no one can use the same username 
 * 
 * @author gIlias
 */

package george.javawebemail.Entities.EmbeddableID;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;

@Data
@Embeddable
@JsonFilter("userEmbeddedIdFilter")
public class UserEmbeddableId implements Serializable {

    /**
     * generated serial version uid
     */
    private static final long serialVersionUID = -492529989482046563L;

    @NotNull
    private String userName;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserEmbeddableId() {

    }

    public UserEmbeddableId(String userName, Long id) {
        this.userName = userName;
        this.id = id;
    }

    public UserEmbeddableId(String userName) {
        this.userName = userName;

    }

}