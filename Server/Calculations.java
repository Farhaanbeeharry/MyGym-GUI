import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class Calculations {

    public static long getDuration(String timeSlot) {
        long duration = 1;
        try {
            String startTime = timeSlot.substring(0, 5) + ":00";
            String endTime = timeSlot.substring(6, 11) + ":00";

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date1 = format.parse(startTime);
            Date date2 = format.parse(endTime);
            duration = date2.getTime() - date1.getTime();
            duration = (duration / 1000 / 60);

        } catch (ParseException ex) {
            System.out.println("Duration calculation error!");
        }
        return duration;
    }

    public static int checkTime(String timeInput, ArrayList<String> timeBooked) {

        int check = 0;
        String startHour = timeInput.substring(0, 2) + "00";
        String endHour = timeInput.substring(6, 8) + "00";
        String startMinute = timeInput.substring(3, 5);
        String endMinute = timeInput.substring(9, 11);
        String startBookedHour, endBookedHour, startBookedMinute, endBookedMinute;
        int startTime = Integer.parseInt(startHour) + Integer.parseInt(startMinute);
        int endTime = Integer.parseInt(endHour) + Integer.parseInt(endMinute);

        for (int i = 0; i < timeBooked.size(); i++) {
            startBookedHour = timeBooked.get(i).substring(0, 2) + "00";
            startBookedMinute = timeBooked.get(i).substring(3, 5);
            endBookedHour = timeBooked.get(i).substring(6, 8) + "00";
            endBookedMinute = timeBooked.get(i).substring(9, 11);
            int startBookedTime = Integer.parseInt(startBookedHour) + Integer.parseInt(startBookedMinute);
            int endBookedTime = Integer.parseInt(endBookedHour) + Integer.parseInt(endBookedMinute);

            if (startTime == startBookedTime) {
                check = 1;
            }
            if (endTime == endBookedTime) {
                check = 1;
            }

            if ((startTime > startBookedTime) && (startTime < endBookedTime)) {
                check = 1;
            }
            if ((endTime > startBookedTime) && (endTime < endBookedTime)) {
                check = 1;
            }
	    
	    if ((startTime < startBookedTime) && (endTime > endBookedTime)) {
		check = 1;
	    }

        }

        return check;
    }

    public static int checkValidDate(String dateToValidate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        simpleDateFormat.setLenient(false);

        try {

            Date enteredDate = simpleDateFormat.parse(dateToValidate);

        } catch (ParseException e) {

            return 1;
        }

        return 2;
    }

    public static int checkValidFutureDate(String dateToValidate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date currentDate = new Date();

        simpleDateFormat.setLenient(false);

        try {

            Date enteredDate = simpleDateFormat.parse(dateToValidate);

            if (!enteredDate.after(currentDate)) {
                return 0;
            }

        } catch (ParseException e) {

            return 1;
        }

        return 2;
    }

    public static int checkValidTime(String timeToValidate) {

        if (!String.valueOf(timeToValidate.charAt(5)).equalsIgnoreCase("-")) {
            return 1;
        }

        String startTime = timeToValidate.substring(0, 5);
        String endTime = timeToValidate.substring(6, 11);
        try {
            LocalTime.parse(startTime);
        } catch (DateTimeParseException | NullPointerException e) {
            return 2;
        }

        try {
            LocalTime.parse(endTime);
        } catch (DateTimeParseException | NullPointerException e) {
            return 3;
        }

        String startHour = timeToValidate.substring(0, 2) + "00";
        String endHour = timeToValidate.substring(6, 8) + "00";
        String startMinute = timeToValidate.substring(3, 5);
        String endMinute = timeToValidate.substring(9, 11);
        int startConvertedTime = Integer.parseInt(startHour) + Integer.parseInt(startMinute);
        int endConvertedTime = Integer.parseInt(endHour) + Integer.parseInt(endMinute);

        if (startConvertedTime < 600) {
            return 4;
        }

        if (endConvertedTime > 2300) {
            return 5;
        }

        if (startConvertedTime > endConvertedTime) {
            return 6;
        }

        if (startConvertedTime == endConvertedTime) {
            return 7;
        }

        long duration = Calculations.getDuration(timeToValidate);
        if (duration < 30) {
            return 8;
        }

        return 9;

    }

}
