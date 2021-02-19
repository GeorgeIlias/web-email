package george.javawebemail.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
@Table(name = "UserFolders")
@JsonFilter(value = "userFoldersFilter")
public class UserFolders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String folderNames;

    @ManyToOne
    @JoinColumn(name="userFolderTable")
    private User user;

    public UserFolders() {

    }

    public UserFolders(String folderNames, User user) {
        this.folderNames = folderNames;
        this.user = user;
    }

}