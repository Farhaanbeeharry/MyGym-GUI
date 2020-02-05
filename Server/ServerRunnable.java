import java.net.*;
import java.io.*;
import java.util.*;

public class ServerRunnable implements Runnable {

    private Socket socket;

    public ServerRunnable(Socket sk) {
        socket = sk;
    }

    public void run() {

        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                String[] userInput = (String[]) objectInputStream.readObject();

                if (userInput[0].equals("LISTALL")) {

                    if (userInput.length > 1) {
                        objectOutputStream.writeObject("Too many arguments !\n");
                        objectOutputStream.reset();
                    } else {
                        ArrayList<Booking> allBookings = new ArrayList<Booking>();
                        allBookings = DataImport.getAllBookings(allBookings);

                        ArrayList<String> returnString = new ArrayList();

                        for (int x = 0; x < allBookings.size(); x++) {

                            returnString.add(allBookings.get(x).getBookingID() + "#"
                                    + allBookings.get(x).getCustomerID() + "#"
                                    + allBookings.get(x).getTrainerID() + "#"
                                    + allBookings.get(x).getSpecialismID() + "#"
                                    + allBookings.get(x).getTimeSlot() + "#"
                                    + allBookings.get(x).getDate() + "#"
                                    + allBookings.get(x).getDuration()
                            );
                        }

                        objectOutputStream.writeObject(returnString);
                        objectOutputStream.reset();

                    }
                } else if (userInput[0].equals("LISTPT")) {

                    if ((userInput.length == 2) && (userInput[1].length() == 5)) {
                        ArrayList<Booking> bookingsTrainerID = new ArrayList<Booking>();
                        bookingsTrainerID = DataImport.getBookingsTrainerID(bookingsTrainerID, userInput[1]);

                        ArrayList<String> returnString = new ArrayList();

                        for (int x = 0; x < bookingsTrainerID.size(); x++) {

                            returnString.add(bookingsTrainerID.get(x).getBookingID() + "#"
                                    + bookingsTrainerID.get(x).getCustomerID() + "#"
                                    + bookingsTrainerID.get(x).getTrainerID() + "#"
                                    + bookingsTrainerID.get(x).getSpecialismID() + "#"
                                    + bookingsTrainerID.get(x).getTimeSlot() + "#"
                                    + bookingsTrainerID.get(x).getDate() + "#"
                                    + bookingsTrainerID.get(x).getDuration()
                            );
                        }

                        if (DataImport.checkExistTrainerID(userInput[1]) == 0) {
                            returnString.add("NotFound");
                        }

                        objectOutputStream.writeObject(returnString);
                        objectOutputStream.reset();
                    }

                } else if (userInput[0].equals("LISTCLIENT")) {

                    if ((userInput.length == 2) && (userInput[1].length() == 5)) {
                        ArrayList<Booking> bookingsClientID = new ArrayList<Booking>();
                        bookingsClientID = DataImport.getBookingsCustomerID(bookingsClientID, userInput[1]);

                        ArrayList<String> returnString = new ArrayList();

                        for (int x = 0; x < bookingsClientID.size(); x++) {

                            returnString.add(bookingsClientID.get(x).getBookingID() + "#"
                                    + bookingsClientID.get(x).getCustomerID() + "#"
                                    + bookingsClientID.get(x).getTrainerID() + "#"
                                    + bookingsClientID.get(x).getSpecialismID() + "#"
                                    + bookingsClientID.get(x).getTimeSlot() + "#"
                                    + bookingsClientID.get(x).getDate() + "#"
                                    + bookingsClientID.get(x).getDuration()
                            );
                        }

                        if (DataImport.checkExistClientID(userInput[1]) == 0) {
                            returnString.add("NotFound");
                        }

                        objectOutputStream.writeObject(returnString);
                        objectOutputStream.reset();
                    }

                } else if (userInput[0].equals("LISTDAY")) {

                    if ((userInput.length == 2) && (Calculations.checkValidDate(userInput[1]) == 2)) {
                        ArrayList<Booking> bookingsDate = new ArrayList<Booking>();
                        bookingsDate = DataImport.getBookingsDate(bookingsDate, userInput[1]);

                        ArrayList<String> returnString = new ArrayList();

                        for (int x = 0; x < bookingsDate.size(); x++) {

                            returnString.add(bookingsDate.get(x).getBookingID() + "#"
                                    + bookingsDate.get(x).getCustomerID() + "#"
                                    + bookingsDate.get(x).getTrainerID() + "#"
                                    + bookingsDate.get(x).getSpecialismID() + "#"
                                    + bookingsDate.get(x).getTimeSlot() + "#"
                                    + bookingsDate.get(x).getDate() + "#"
                                    + bookingsDate.get(x).getDuration()
                            );
                        }

                        objectOutputStream.writeObject(returnString);
                        objectOutputStream.reset();
                    }

                } else if (userInput[0].equals("DELETE")) {

                    String returnMessage = "";

                    if (userInput.length < 2) {
                        returnMessage = "Not enough arguments !\n";
                    } else if (userInput.length > 2) {
                        returnMessage = "Too many arguments !\n";
                    } else if (userInput[1].length() != 5) {
                        returnMessage = "BookingID can have 5 characters only !\n";
                    } else if (!userInput[1].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "BookingID should be alphanumeric !\n";
                    } else {

                        int returnValue = DataExport.deleteBooking(userInput[1]);

                        if (returnValue == 0) {
                            returnMessage = "BookingID does not exist !\n";
                        } else if (returnValue == 1) {
                            returnMessage = "Delete failed !\n";
                        } else if (returnValue == 2) {
                            returnMessage = "Delete successful !\n";
                        }

                    }

                    objectOutputStream.writeObject(returnMessage);
                    objectOutputStream.reset();

                } else if (userInput[0].equals("ADD")) {

                    String returnMessage = "";

                    if (userInput.length < 6) {
                        returnMessage = "Not enough arguments !\n";
                    } else if (userInput.length > 6) {
                        returnMessage = "Too many arguments !\n";
                    } else if (userInput[1].length() != 5) {
                        returnMessage = "CustomerID can have 5 characters only !\n";
                    } else if (!userInput[1].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "CustomerID should be alphanumeric !\n";
                    } else if (DataImport.checkExistClientID(userInput[1]) == 0) {
                        returnMessage = "CustomerID not found ! Contact ADMIN for new Customer !\n";
                    } else if (userInput[2].length() != 5) {
                        returnMessage = "TrainerID can have 5 characters only !\n";
                    } else if (!userInput[2].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "TrainerID should be alphanumeric !\n";
                    } else if (DataImport.checkExistTrainerID(userInput[2]) == 0) {
                        returnMessage = "TrainerID not found !\n";
                    } else if (userInput[3].length() != 5) {
                        returnMessage = "SpecialismID can have 5 characters only !\n";
                    } else if (!userInput[3].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "SpecialismID should be alphanumeric !\n";
                     } else if (DataImport.checkExistSpecialismID(userInput[3], userInput[2]) == 0) {
                        returnMessage = "SpecialismID not found for this TrainerID !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 1) {
                        returnMessage = "Wrong time format !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 2) {
                        returnMessage = "Wrong start time !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 3) {
                        returnMessage = "Wrong end time !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 4) {
                        returnMessage = "MyGym opens at 06:00 !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 5) {
                        returnMessage = "MyGym closes at 23:00 !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 6) {
                        returnMessage = "Start time cannot be after End time !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 7) {
                        returnMessage = "Session time cannot be 0 minutes !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 8) {
                        returnMessage = "Minimum session time is 30 minutes !\n";
                    } else if (Calculations.checkValidFutureDate(userInput[5]) == 1) {
                        returnMessage = "Wrong Date format !\n";
                    } else if (Calculations.checkValidFutureDate(userInput[5]) == 0) {
                        returnMessage = "Date should be in the future !\n";
                    } else {

                        int returnValue = DataExport.addBooking(userInput[1], userInput[2], userInput[3], userInput[4], userInput[5]);

                        if (returnValue == 0) {
                            returnMessage = "Booking failed !\n";
                        } else if (returnValue == 1) {
                            returnMessage = "Trainer already booked !\n";
                        } else if (returnValue == 2) {
                            returnMessage = "Booking successful !\n";
                        }
                    }

                    objectOutputStream.writeObject(returnMessage);
                    objectOutputStream.reset();

                } else if (userInput[0].equals("UPDATE")) {

                    String returnMessage = "";

                    if (userInput.length < 6) {
                        returnMessage = "Not enough arguments !\n";
                    } else if (userInput.length > 6) {
                        returnMessage = "Too many arguments !\n";
                    } else if (userInput[1].length() != 5) {
                        returnMessage = "BookingID can have 5 characters only !\n";
                    } else if (!userInput[1].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "BookingID should be alphanumeric !\n";
                    } else if (userInput[2].length() != 5) {
                        returnMessage = "TrainerID can have 5 characters only !\n";
                    } else if (!userInput[2].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "TrainerID should be alphanumeric !\n";
                    } else if (DataImport.checkExistTrainerID(userInput[2]) == 0) {
                        returnMessage = "TrainerID not found !\n";
                    } else if (userInput[3].length() != 5) {
                        returnMessage = "SpecialismID can have 5 characters only !\n";
                    } else if (!userInput[3].matches("[a-zA-Z0-9]+")) {
                        returnMessage = "SpecialismID should be alphanumeric !\n";
                     } else if (DataImport.checkExistSpecialismID(userInput[3], userInput[2]) == 0) {
                        returnMessage = "SpecialismID not found for this TrainerID !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 1) {
                        returnMessage = "Wrong time format !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 2) {
                        returnMessage = "Wrong start time !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 3) {
                        returnMessage = "Wrong end time !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 4) {
                        returnMessage = "MyGym opens at 06:00 !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 5) {
                        returnMessage = "MyGym closes at 23:00 !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 6) {
                        returnMessage = "Start time cannot be after End time !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 7) {
                        returnMessage = "Session time cannot be 0 minutes !\n";
                    } else if (Calculations.checkValidTime(userInput[4]) == 8) {
                        returnMessage = "Minimum session time is 30 minutes !\n";
                    } else if (Calculations.checkValidFutureDate(userInput[5]) == 1) {
                        returnMessage = "Wrong Date format !\n";
                    } else if (Calculations.checkValidFutureDate(userInput[5]) == 0) {
                        returnMessage = "Date should be in the future !\n";
                    } else if (DataImport.checkSameData(userInput[1], userInput[2], userInput[3], userInput[4], userInput[5]) == 1) {
                        returnMessage = "Data is same as existing. Update cancelled !\n";
                    } else {

                        int returnValue = DataExport.updateBooking(userInput[1], userInput[2], userInput[3], userInput[4], userInput[5]);

                        if (returnValue == -1) {
                            returnMessage = "Update failed !\n";
                        } else if (returnValue == 0) {
                            returnMessage = "BookingID does not exist !\n";
                        } else if (returnValue == 2) {
                            returnMessage = "Trainer already Booked!\n";
                        } else if (returnValue == 3) {
                            returnMessage = "Update successful !\n";
                        }
                    }

                    objectOutputStream.writeObject(returnMessage);
                    objectOutputStream.reset();

                } else {
                    objectOutputStream.writeObject("Invalid Command !\n");
                    objectOutputStream.reset();
                }
            }

        } catch (SocketException e) {
            System.out.println("Client Disconnected with socket " + socket + " !");
        } catch (IOException e) {
            System.out.println("I/O Error !");
        } catch (Exception e) {
            System.out.println("Error !");
        }

    }

}

