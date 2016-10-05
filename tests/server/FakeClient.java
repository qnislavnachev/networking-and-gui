package server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class FakeClient extends Thread {
    private String message;
    private String host;
    private int port;

    public FakeClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket fakeClient = new Socket(host, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fakeClient.getInputStream()));
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (SocketException ex) {
            System.out.println("Server fall down !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }
}
