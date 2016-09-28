package serverTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class FakeClient {
    String message = null;

    public void connect(String host, int port) {
        try {
            Socket fakeClient = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fakeClient.getInputStream()));
            message = reader.readLine();
            fakeClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }
}
