import java.io.Serializable;

public class Booking implements Serializable {

    private String bookingID;
    private String customerID;
    private String trainerID;
    private String specialismID;
    private String timeSlot;
    private String date;
    private long duration;

    public Booking() {
        this.bookingID = "";
        this.customerID = "";
        this.trainerID = "";
        this.specialismID = "";
        this.timeSlot = "";
        this.date = "";
        this.duration = 0;
    }

    public Booking(String bookingID, String customerID, String trainerID, String specialismID, String timeSlot,
            String date, long duration) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.trainerID = trainerID;
        this.specialismID = specialismID;
        this.timeSlot = timeSlot;
        this.date = date;
        this.duration = duration;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }

    public String getSpecialismID() {
        return specialismID;
    }

    public void setSpecialismID(String specialismID) {
        this.specialismID = specialismID;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}
