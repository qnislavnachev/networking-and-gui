package task3.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private int port;
    private String message;
    private ServerSocket server = null;
    private List<Socket> listOfClients;

    public Server(int port) {
        this.port = port;
        listOfClients = new ArrayList<>();
    }

    public void run() {
        try {
            server = new ServerSocket(port);
            System.out.println("Server is set up... ");
            while (true) {
                Socket socket = server.accept();
                listOfClients.add(socket);
                System.out.println("New client detected");
                ClientNotifier notifier = new ClientNotifier(listOfClients);
                notifier.start();
                serverListener(socket);

            }
        } catch (SocketException ex) {
            System.out.println("Server was closed !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    private void serverListener(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (SocketException ex) {
            System.out.println("Client has left the server !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
