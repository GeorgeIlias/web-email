package george.javawebemail.Entities;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.sym.Name;

import lombok.Data;


@Data
@Entity
@Table(name = "UserFolders")
@JsonFilter(value="userFoldersFilter")
public class UserFolders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private List<String> folderNames;

    @Column
    @OneToOne(mappedBy = "folders")
    private User user;

    public UserFolders() {

    }

    public UserFolders(List<String> folderNames, User user) {
        this.folderNames = folderNames;
        this.user = user;
    }

   
}