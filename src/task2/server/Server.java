package task2.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        Date date = new Date();
        ServerSocket server = null;
        Socket connection = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server is set up and waiting for users...");
            while (true) {
                connection = server.accept();
                System.out.println("User log into server!");
                PrintStream printer = new PrintStream(connection.getOutputStream());
                printer.println("Hello from the server ! " + date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
