import java.util.ArrayList;

public class Display {

    public static void displayAllBookings(ArrayList<Booking> allBookings) {

        System.out.printf(
                "____________________________________________________________________________________________________________________________________________________\n");
        System.out.printf("| %20s %20s %20s %20s %20s %20s %20s", "   BookingID     |", "CustomerID    |",
                "TrainerID     |", "SpecialismID    |", "Time Slot     |", " Date Booked    |",
                " Session Duration  |\n");
        System.out.printf(
                "====================================================================================================================================================\n");
        for (int x = 0; x < allBookings.size(); x++) {

            System.out.format("| %20s %20s %20s %20s %20s %20s %20s", allBookings.get(x).getBookingID() + "  |",
                    allBookings.get(x).getCustomerID() + "  |", allBookings.get(x).getTrainerID() + "  |",
                    allBookings.get(x).getSpecialismID() + "  |", allBookings.get(x).getTimeSlot() + "  |",
                    allBookings.get(x).getDate() + "  |", allBookings.get(x).getDuration() + " minutes |");
            System.out.println(" ");

        }
        System.out.printf(
                "----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n");
    }

    public static void displayBookingsTrainerID(ArrayList<Booking> bookingsTrainerID) {
        System.out.printf(
                "_______________________________________________________________________________________________________________________________\n");
        System.out.printf("| %20s %20s %20s %20s %20s %20s", "   BookingID     |", "CustomerID    |",
                "SpecialismID    |", "Time Slot     |", " Date Booked    |", " Session Duration  |\n");
        System.out.printf(
                "===============================================================================================================================\n");
        for (int x = 0; x < bookingsTrainerID.size(); x++) {

            System.out.format("| %20s %20s %20s %20s %20s %20s", bookingsTrainerID.get(x).getBookingID() + "  |",
                    bookingsTrainerID.get(x).getCustomerID() + "  |",
                    bookingsTrainerID.get(x).getSpecialismID() + "  |", bookingsTrainerID.get(x).getTimeSlot() + "  |",
                    bookingsTrainerID.get(x).getDate() + "  |", bookingsTrainerID.get(x).getDuration() + " minutes |");
            System.out.println(" ");

        }
        System.out.printf(
                "-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n");
    }

    public static void displayBookingsCustomerID(ArrayList<Booking> bookingCustomerID) {
        System.out.printf(
                "_______________________________________________________________________________________________________________________________\n");
        System.out.printf("| %20s %20s %20s %20s %20s %20s", "   BookingID     |", "TrainerID    |",
                "SpecialismID    |", "Time Slot     |", " Date Booked    |", " Session Duration  |\n");
        System.out.printf(
                "===============================================================================================================================\n");
        for (int x = 0; x < bookingCustomerID.size(); x++) {

            System.out.format("| %20s %20s %20s %20s %20s %20s", bookingCustomerID.get(x).getBookingID() + "  |",
                    bookingCustomerID.get(x).getTrainerID() + "  |", bookingCustomerID.get(x).getSpecialismID() + "  |",
                    bookingCustomerID.get(x).getTimeSlot() + "  |", bookingCustomerID.get(x).getDate() + "  |",
                    bookingCustomerID.get(x).getDuration() + " minutes |");
            System.out.println(" ");

        }
        System.out.printf(
                "-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n");
    }

    public static void displayBookingsDate(ArrayList<Booking> bookingsDate) {
        System.out.printf(
                "_______________________________________________________________________________________________________________________________\n");
        System.out.printf("| %20s %20s %20s %20s %20s %20s", "   BookingID     |", "CustomerID    |", "TrainerID    |",
                "SpecialismID     |", " Time Slot    |", " Session Duration  |\n");
        System.out.printf(
                "===============================================================================================================================\n");
        for (int x = 0; x < bookingsDate.size(); x++) {

            System.out.format("| %20s %20s %20s %20s %20s %20s", bookingsDate.get(x).getBookingID() + "  |",
                    bookingsDate.get(x).getCustomerID() + "  |", bookingsDate.get(x).getTrainerID() + "  |",
                    bookingsDate.get(x).getSpecialismID() + "  |", bookingsDate.get(x).getTimeSlot() + "  |",
                    bookingsDate.get(x).getDuration() + " minutes |");
            System.out.println(" ");

        }
        System.out.printf(
                "-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n");
    }

}
