package com.clouway.task3;

import java.io.*;
import java.net.Socket;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {
    private BufferedReader in = null;
    private Screen screen;

    public Client(Screen screen) {
        this.screen = screen;
    }

    public void connect(String host, int port) throws IOException, NoSocketException {
        Socket client = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        new Thread() {
            @Override
            public void run() {
                try {
                    hasReceivedMessage();
                } catch (IOException e) {
                } catch (NoSocketException e) {
                    screen.display("Server is offline!");
                }
            }
        }.start();
    }

    private void hasReceivedMessage() throws IOException, NoSocketException {
        String fromServer;
        while ((fromServer = in.readLine()) != null) {
            screen.display(fromServer);
        }
        throw new NoSocketException();
    }
}
