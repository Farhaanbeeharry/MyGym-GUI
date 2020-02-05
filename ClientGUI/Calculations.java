import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static int checkValidDate(String dateToValidate) {

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

}
