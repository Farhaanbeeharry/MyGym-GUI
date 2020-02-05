import java.sql.*;
import java.util.*;

public class DataImport {

    public static ArrayList<Booking> getAllBookings(ArrayList<Booking> allBookings) {

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Booking ORDER BY DateBooked;");

            while (resultSet.next()) {
                String bookingID = resultSet.getString("BookingID");
                String customerID = resultSet.getString("CustomerID");
                String trainerID = resultSet.getString("TrainerID");
                String specialismID = resultSet.getString("SpecialismID");
                String timeSlot = resultSet.getString("TimeSlot");
                String date = resultSet.getString("DateBooked");
                long duration = Calculations.getDuration(timeSlot);

                Booking newBooking = new Booking(bookingID, customerID, trainerID, specialismID, timeSlot, date,
                        duration);

                allBookings.add(newBooking);

            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return allBookings;
    }

    public static ArrayList<Booking> getBookingsTrainerID(ArrayList<Booking> bookingsTrainerID, String condition) {

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Booking WHERE TrainerID='" + condition + "' ORDER BY DateBooked;");

            while (resultSet.next()) {
                String bookingID = resultSet.getString("BookingID");
                String customerID = resultSet.getString("CustomerID");
                String trainerID = resultSet.getString("TrainerID");
                String specialismID = resultSet.getString("SpecialismID");
                String timeSlot = resultSet.getString("TimeSlot");
                String date = resultSet.getString("DateBooked");
                long duration = Calculations.getDuration(timeSlot);

                Booking newBooking = new Booking(bookingID, customerID, trainerID, specialismID, timeSlot, date,
                        duration);

                bookingsTrainerID.add(newBooking);

            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return bookingsTrainerID;
    }

    public static ArrayList<Booking> getBookingsCustomerID(ArrayList<Booking> bookingsCustomerID, String condition) {

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Booking WHERE CustomerID='" + condition + "' ORDER BY DateBooked;");

            while (resultSet.next()) {
                String bookingID = resultSet.getString("BookingID");
                String customerID = resultSet.getString("CustomerID");
                String trainerID = resultSet.getString("TrainerID");
                String specialismID = resultSet.getString("SpecialismID");
                String timeSlot = resultSet.getString("TimeSlot");
                String date = resultSet.getString("DateBooked");
                long duration = Calculations.getDuration(timeSlot);

                Booking newBooking = new Booking(bookingID, customerID, trainerID, specialismID, timeSlot, date,
                        duration);

                bookingsCustomerID.add(newBooking);

            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return bookingsCustomerID;
    }

    public static ArrayList<Booking> getBookingsDate(ArrayList<Booking> bookingsDate, String condition) {

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Booking WHERE DateBooked='" + condition + "' ORDER BY CustomerID;");

            while (resultSet.next()) {
                String bookingID = resultSet.getString("BookingID");
                String customerID = resultSet.getString("CustomerID");
                String trainerID = resultSet.getString("TrainerID");
                String specialismID = resultSet.getString("SpecialismID");
                String timeSlot = resultSet.getString("TimeSlot");
                String date = resultSet.getString("DateBooked");
                long duration = Calculations.getDuration(timeSlot);

                Booking newBooking = new Booking(bookingID, customerID, trainerID, specialismID, timeSlot, date,
                        duration);

                bookingsDate.add(newBooking);

            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return bookingsDate;
    }

    public static ArrayList<String> getBookedTime(String trainerID, String date) {

        ArrayList<String> bookedTime = new ArrayList();

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Booking WHERE TrainerID='" + trainerID + "' && DateBooked='" + date + "';");

            while (resultSet.next()) {
                String booked = resultSet.getString("TimeSlot");

                bookedTime.add(booked);

            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return bookedTime;
    }

    public static ArrayList<String> getBookedTimeExcludeClient(String trainerID, String date, String customerID) {

        ArrayList<String> bookedTime = new ArrayList();

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Booking WHERE TrainerID='" + trainerID
                    + "' AND DateBooked='" + date + "' AND CustomerID!='" + customerID + "';");

            while (resultSet.next()) {
                String booked = resultSet.getString("TimeSlot");

                bookedTime.add(booked);

            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return bookedTime;
    }

    public static int checkExistTrainerID(String trainerID) {

        int result = -1;

        Connection connection = ConnectionManager.getConnection();

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement
                    .executeQuery("SELECT EXISTS(SELECT * FROM Trainer where TrainerID='" + trainerID + "');");

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }
        return result;

    }

    public static int checkExistClientID(String clientID) {

        int result = -1;

        Connection connection = ConnectionManager.getConnection();

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement
                    .executeQuery("SELECT EXISTS(SELECT * FROM Customer where CustomerID='" + clientID + "');");

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }
        return result;

    }

    public static int checkExistSpecialismID(String specialismID, String trainerID) {

         int result = -1;

        Connection connection = ConnectionManager.getConnection();

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(SELECT * FROM Focus where SpecialismID='" + specialismID + "' && TrainerID='"+ trainerID + "');");

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }
        return result;

    }

    public static int checkSameData(String bookingID, String trainerID, String specialismID, String timeSlot,
            String date) {

        int check = 0;
        String existTrainerID = "", existSpecialismID = "", existTimeSlot = "", existDate = "";

        Connection connection = ConnectionManager.getConnection();

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Booking WHERE BookingID='" + bookingID + "';");

            while (resultSet.next()) {
                existTrainerID = resultSet.getString("TrainerID");
                existSpecialismID = resultSet.getString("SpecialismID");
                existTimeSlot = resultSet.getString("TimeSlot");
                existDate = resultSet.getString("DateBooked");

            }

            if (existTrainerID.equals(trainerID) && existSpecialismID.equals(specialismID)
                    && existTimeSlot.equals(timeSlot) && existDate.equals(date)) {
                return 1;
            }

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return check;
    }

}
