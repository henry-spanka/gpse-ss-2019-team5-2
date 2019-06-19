package gpse.team52.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DBFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    private String fileType;

    @Lob
    private byte[] data;

    public DBFile() {

    }

    public DBFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }


}
