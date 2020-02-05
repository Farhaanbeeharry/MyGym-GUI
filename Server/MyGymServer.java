import java.io.*;
import java.net.*;

public class MyGymServer {

    public static void main(String[] args) {

        final int PORT = 8888;
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected with socket " + socket + " !");
                new Thread(new ServerRunnable(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }

    }
}
