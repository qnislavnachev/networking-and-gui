package task2.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    private int port;
    private ServerDate serverDate;

    public Server(int port, ServerDate serverDate) {
        this.port = port;
        this.serverDate = serverDate;
    }

    private void closeQuietly(ServerSocket server) {
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeQuietly(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        Date date = serverDate.getServerDate();
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server is set up and waiting for users...");
            socket = server.accept();
            System.out.println("User log into server!");
            PrintStream printer = new PrintStream(socket.getOutputStream());
            printer.println("Hello from the server ! " + date);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(server);
            closeQuietly(socket);
        }
    }
}