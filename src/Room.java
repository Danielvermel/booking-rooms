import java.io.PrintStream;
import java.util.ArrayList;

public class Room {

    //Variables
    private String roomName;
    private int roomId;
    private int floor;
    private String typeRoom;
    private int capacity;


    //Constructor
    public Room(String roomName, int roomId){
        this.roomName = roomName;
        this.roomId = roomId;
    }

    public Room(String roomName, int floor,String typeRoom, int capacity, int roomId){
        this.roomName = roomName;
        this.floor = floor;
        this.typeRoom = typeRoom;
        this.capacity = capacity;
        this.roomId = roomId;
    }

    //Methods
    public  String getRoomName(){
        return roomName;
    }

    public int getFloor(){
        return floor;
    }

    public String getTypeRoom(){
        return typeRoom;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getRoomId(){
        return roomId;
    }

    public void setRoomId(int roomId){ this.roomId = roomId; }

    public void setRoomName(String roomName) {this.roomName = roomName; }

    @Override
    public String toString() {
        return "Name: "+getRoomName() + " | Floor: " + getFloor() + " | Type of Room: " + getTypeRoom()+" | Capacity: "+getCapacity();
    }
}
