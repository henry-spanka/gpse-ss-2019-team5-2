package gpse.team52.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Meeting {
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    protected long meetingID;

    @Getter
    @Setter
    @Column
    protected LocalDateTime dateTime;

    @Getter
    @Setter
    @Column
    protected String subject;

    @Getter
    @Setter
    @Column
    protected int attendance;

    @OneToMany
    protected User attendant;

    public Meeting (){

    }

}
