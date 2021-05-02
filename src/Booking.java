import java.math.RoundingMode;
import java.util.ArrayList;

public class Booking{

    //Variables
    private String firstName;
    private String lastName;
    private String bookingDate;
    private String bookingHours;
    private Room room;

    //Constructor
    public Booking(String firstName, String lastName, int roomId, String roomName){
        //Composition
        this.room = new Room(roomName,roomId);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //methods
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public  String getRoomName(){ return room.getRoomName(); }

    public int getRoomId(){
        return room.getRoomId();
    }

    public void setRoomId(int roomId){ room.setRoomId(roomId); }

    public void setRoomName(String roomName) { room.setRoomName(roomName); }

    public void setBookingHours(int chooseHour) { this.bookingHours = Integer.toString(chooseHour); }

    public void setBookingDate(String chooseDate) { this.bookingDate = chooseDate; }

    public boolean isValidBookingDate(String date){
        try{ // checks if the date exit
            return bookingDate.equals(date);
        }catch(Exception e){
            return false;
        }
    }

    public String getBookingDate() { return bookingDate; }

    public boolean getBookingHours(int i) { return bookingHours.equals(Integer.toString(i)); }

    public String getBookingHours() {
        String begin = Integer.toString( Integer.parseInt(bookingHours)-1); // booking begin is 1h apart from the end
        String end = bookingHours ;
        String bookingHour = begin + ":00 to "+ end +":00";
        return bookingHour;
    }


    @Override
    public String toString() {
        return "Mr/Miss " + this.firstName + " " + this.lastName + ", you just booked the room " + this.room.getRoomName() + " for " + this.getBookingDate() + " between " + this.getBookingHours() + "." ;
    }

}
