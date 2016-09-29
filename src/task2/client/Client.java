package task2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private String message = null;

    public void join(String host, int port) {
        try (Socket client = new Socket(host, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            message = reader.readLine();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("You left the server!");
    }

    public String getMessage() {
        return message;
    }
}
