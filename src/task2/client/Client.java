package task2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public void join(String host, int port) {
        Socket client = null;
        try {
            client = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String message = reader.readLine();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("You left the server!");
    }
}
