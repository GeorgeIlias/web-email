/**
 * Embeddable username id pair for a user account so that no one can use the same username 
 * 
 * @author gIlias
 */

package george.javawebemail.Entities.EmbeddableID;

import java.io.Serializable;


public class UserEmbeddableId implements Serializable {

    /**
     * generated serial version uid
     */
    private static final long serialVersionUID = -492529989482046563L;

    private String userName;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

}