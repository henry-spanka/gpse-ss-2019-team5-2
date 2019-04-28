package gpse.team52;

public class Room {

    private final int ID;
    private String name;
    private int seats;
    private String equipment;

    public Room(String name, int seats, String equipment, int id) {
        this.name = name;
        this.seats = seats;
        this.equipment =  equipment;
        this.ID = id;
    }

    public String getName() {
        return name;
    }
    public String getEquipment() {
        return equipment;
    }
    public int getSeats() {
        return seats;
    }
    public int getID() { return ID; }

}
