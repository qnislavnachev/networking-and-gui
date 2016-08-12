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

    public void connect(String host, int port) throws IOException {
        Socket client = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        new Thread() {
            @Override
            public void run() {
                try {
                    receiveMessage();
                } catch (IOException e) {
                } catch (NoSocketException e) {
                    screen.display("Server is offline!");
                }
            }
        }.start();
    }

    private void receiveMessage() throws IOException, NoSocketException {
        String fromServer;
        while (true) {
            if((fromServer = in.readLine()) != null) {
                screen.display(fromServer);
            }else{
                throw new NoSocketException();
            }
        }
    }
}
