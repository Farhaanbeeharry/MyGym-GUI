import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataExport {

    public static ReentrantLock threadLock = new ReentrantLock();

    public static int deleteBooking(String bookingID) {

        int result = -1;

        Connection connection = ConnectionManager.getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement
                    .executeQuery("SELECT EXISTS(SELECT * FROM Booking where BookingID='" + bookingID + "');");

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

            if (result == 0) {
                return result;
            }

            statement.executeUpdate("DELETE FROM Booking WHERE BookingID='" + bookingID + "';");
            result = 2;

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

    public static int addBooking(String customerID, String trainerID, String specialismID, String timeSlot,
            String date) {

        int finalResult = -1;

        threadLock.lock();

        try {

            int resultTrainerID = -1;
            int resultDate = -1;

            Connection connection = ConnectionManager.getConnection();

            try {

                finalResult = 0;

                Statement statement = connection.createStatement();

                ResultSet resultSetTrainerID = statement
                        .executeQuery("SELECT EXISTS(SELECT * FROM Booking where TrainerID='" + trainerID + "');");

                if (resultSetTrainerID.next()) {
                    resultTrainerID = resultSetTrainerID.getInt(1);
                }

                ResultSet resultSetDate = statement
                        .executeQuery("SELECT EXISTS(SELECT * FROM Booking where DateBooked='" + date + "');");

                if (resultSetDate.next()) {
                    resultDate = resultSetDate.getInt(1);
                }

                ArrayList<String> timeBooked = DataImport.getBookedTime(trainerID, date);

                int check = 0;

                if ((resultTrainerID == 1) && (resultDate == 1)) {
                    check = Calculations.checkTime(timeSlot, timeBooked);
                }

                if (check == 1) {
                    finalResult = 1;
                    return finalResult;
                }

                String bookingID = generateBookingID();

                statement.executeUpdate("INSERT INTO Booking VALUES('" + bookingID + "', '" + customerID + "', '"
                        + trainerID + "', '" + specialismID + "', '" + timeSlot + "', '" + date + "');");
                finalResult = 2;

            } catch (SQLException ex) {
                System.out.println("\nSQL Statement Error!");
            }

            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("\nError closing connection to database!");
            }

        } finally {
            threadLock.unlock();

        }

        return finalResult;
    }

    public static int updateBooking(String bookingID, String trainerID, String specialismID, String timeSlot,
            String date) {

        int finalResult = -1;

        threadLock.lock();

        try {

            String customerID = "";

            Connection connection = ConnectionManager.getConnection();

            try {
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement
                        .executeQuery("SELECT EXISTS(SELECT * FROM Booking where BookingID='" + bookingID + "');");

                if (resultSet.next()) {
                    finalResult = resultSet.getInt(1);
                }

                if (finalResult == 0) {
                    return finalResult;
                }

                ResultSet getCustomerID = statement
                        .executeQuery("SELECT * FROM Booking WHERE BookingID='" + bookingID + "';");

                while (getCustomerID.next()) {
                    customerID = getCustomerID.getString("CustomerID");
                }

                int result = addUpdateBooking(bookingID, customerID, trainerID, specialismID, timeSlot, date);

                if (result == 0) {
                    finalResult = -1;
                    return finalResult;
                } else if (result == 1) {
                    finalResult = 2;
                    return finalResult;
                } else {
                    finalResult = 3;
                    return finalResult;
                }

            } catch (SQLException ex) {
                System.out.println("\nSQL Statement Error!");
            }

            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("\nError closing connection to database!");
            }
        } finally {

            threadLock.unlock();
        }

        return finalResult;
    }

    public static int addUpdateBooking(String bookingID, String customerID, String trainerID, String specialismID,
            String timeSlot, String date) {

        int finalResult = -1;
        int resultTrainerID = -1;
        int resultDate = -1;

        Connection connection = ConnectionManager.getConnection();

        try {

            finalResult = 0;

            Statement statement = connection.createStatement();

            ResultSet resultSetTrainerID = statement
                    .executeQuery("SELECT EXISTS(SELECT * FROM Booking where TrainerID='" + trainerID + "');");

            if (resultSetTrainerID.next()) {
                resultTrainerID = resultSetTrainerID.getInt(1);
            }

            ResultSet resultSetDate = statement
                    .executeQuery("SELECT EXISTS(SELECT * FROM Booking where DateBooked='" + date + "');");

            if (resultSetDate.next()) {
                resultDate = resultSetDate.getInt(1);
            }

            ArrayList<String> timeBooked = DataImport.getBookedTimeExcludeClient(trainerID, date, customerID);

            int check = 0;

            if ((resultTrainerID == 1) && (resultDate == 1)) {
                check = Calculations.checkTime(timeSlot, timeBooked);
            }

            if (check == 1) {
                finalResult = 1;
                return finalResult;
            }

            finalResult = 2;

            statement.executeUpdate("UPDATE Booking SET TrainerID='" + trainerID + "', SpecialismID='" + specialismID
                    + "', " + "TimeSlot='" + timeSlot + "', DateBooked='" + date + "' WHERE BookingID='" + bookingID
                    + "';");

        } catch (SQLException ex) {
            System.out.println("\nSQL Statement Error!");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("\nError closing connection to database!");
        }

        return finalResult;
    }

    public static String generateBookingID() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        generatedString = generatedString.toUpperCase();

        return generatedString;
    }

}
