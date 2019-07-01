package gpse.team52.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * DBFile Entity.
 */
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

    /**
     * DBFile Constructor.
     * @param fileName Filename.
     * @param fileType Filetype.
     * @param data Data.
     */
    public DBFile(final String fileName, final String fileType, final byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }


}
