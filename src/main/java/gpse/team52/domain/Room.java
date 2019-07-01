package gpse.team52.domain;

import java.util.*;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


//TODO: - set attributes correct for database
// - set little star if room is a favourite
// - use database to access data in controller
// - roomdetails page
// - testdata (room, equipment) for database
// - unit tests for functions submit, cancel, back
// - submit data for real at last submit step
// - add equipment
// - Belegungsplan für Räume
// - (get back from last step and detailed information with rooms still selected)

/**
 * Room Entity.
 */
@Entity
public class Room {

    /**
     * Unique Id for each room.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false,
    columnDefinition = "BINARY(16)")
    private UUID roomID;

    /**
     * Number of fix seats in a room.
     */
    @Getter
    @Setter
    @Column(nullable = false)
    private int seats;

    /**
     * Number of optional seats for a room.
     */
    @Getter
    @Setter
    @Column(nullable = false)
    private int expandableSeats;

    /**
     * Email address of a room.
     */
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String roomEmail;

    /**
     * Name of a room.
     */
    @Getter
    @Setter
    @Column(nullable = false) //TODO nullable should be false!
    private String roomName;

    /**
     * Location of the room.
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "locationId")
    private Location location;

    /**
     * Meetings held in this room.
     */
    @Getter
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private Set<MeetingRoom> meetings;

    //TODO schauen ob layout klappt
    @Getter
    @Column(nullable = false)
    private String layoutName;

    @Getter
    @ManyToMany(targetEntity = Equipment.class)
    @JoinColumn(nullable = false, name = "equipmentId")
    private List<Equipment> equipment = new ArrayList<>();

    @Getter
    @Setter
    @Column
    private String telephone;

    @Getter
    @Setter
    @Column
    private String notes;

    @Getter
    @Setter
    @Column
    private String office;

    protected Room() {
    }

    /**
     * Constructor for a room.
     *
     * @param seats           Define number of seats
     * @param expandableSeats Define number of optional seats
     * @param email           Email address of the room
     * @param location        Location of the room
     * @param roomName        Name of the room
     * @param layoutName      Layout of the room
     */
    public Room(final int seats, final int expandableSeats,
                final String email, final Location location, final String roomName, final String layoutName) {
        //TODO Konstruktor mit Raumnamen und LayoutNamen am Schluss, ich versuche das überall zu fixen
        // kann sein, dass ich was übersehe
        this.seats = seats;
        this.expandableSeats = expandableSeats;
        this.roomEmail = email;
        this.location = location;
        this.layoutName = layoutName;
        this.roomName = roomName;
    }

    public void addEquipment(final Equipment equipment) {
        this.equipment.add(equipment);
    }

    public void addEquipment(final List<Equipment> equipment) {
        this.equipment.addAll(equipment);
    }

    public void addEquipment(final Equipment... equipments) {
        addEquipment(Arrays.asList(equipments));
    }

    /**
     * Converts a room to a string.
     * @return String.
     */
    public String roomToString() {
        String equi = "";
        if (equipment.size() != 0) {

            equi = equipment.get(0).getEquipmentName();
            for (int i = 1; i < equipment.size(); i++) {
                equi = equi + "," + equipment.get(i).getEquipmentName();

            }
        }

        String room = location.getName() + ";" + roomName + ";" + seats + "+" + expandableSeats + ";" + equi + ";"
        + telephone + ";" + notes + ";" + office + ";" + roomEmail;
        return room;
    }

    /*TODO: passt der pfad?
    TODO: pfad zu ressources passend einfügen
    TODO: wie wird es in die html geladen?
    TODO: im controller anpassen das er das da tut
     */
}
