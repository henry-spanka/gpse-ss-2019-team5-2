package gpse.team52;

public class Room {

    private String name;
    private int seats;
    private String equipment;

    public Room(String name, int seats, String equipment) {
        this.name = name;
        this.seats = seats;
        this.equipment =  equipment;
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

}
