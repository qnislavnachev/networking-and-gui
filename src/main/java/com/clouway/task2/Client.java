package com.clouway.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {
    private String message;
    private Screen screen;

    public Client(Screen screen) {
        this.screen = screen;
    }

    public void connect(final String host, final int port) throws InterruptedException {
        new Thread() {
            @Override
            public void run() {
                String fromServer;
                Socket client = null;
                try {
                    client = new Socket(host, port);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    if ((fromServer = in.readLine()) != null) {
                        message = fromServer;
                        screen.display(message);
                    }
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getMessage() throws InterruptedException {
        return message;
    }
}
