package task2.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    private int port;
    private CurrentDate currentDate;

    public Server(int port, CurrentDate currentDate) {
        this.port = port;
        this.currentDate = currentDate;
    }

    public void start() {
        Date date = currentDate.getCurrentDate();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server is set up and waiting for users...");
            Socket socket = server.accept();
            System.out.println("User log into server!");
            PrintStream printer = new PrintStream(socket.getOutputStream());
            printer.println("Hello from the server ! " + date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}