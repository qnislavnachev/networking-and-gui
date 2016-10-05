package task3.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ServerListener extends Thread {
    private Socket socket;

    public ServerListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String message;
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
