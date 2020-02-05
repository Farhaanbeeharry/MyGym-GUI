import java.util.Scanner;

public class UserInput {

    public static String userInput() {

        Scanner inputScanner = new Scanner(System.in);
        String userInput = inputScanner.next();
        return userInput;
    }
}
