package com.clouway.task3;

import java.io.*;
import java.net.Socket;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {
    private Screen screen;

    public Client(Screen screen) {
        this.screen = screen;
    }

    public void connect(String host, int port) throws IOException {
        Socket client = new Socket(host, port);
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        new Thread() {
            @Override
            public void run() {
                try {
                    listen(input);
                } catch (IOException e) {
                } catch (NoSocketException e) {
                    screen.display("Server is offline!");
                }
            }
        }.start();
    }

    private void listen(BufferedReader in) throws IOException, NoSocketException {
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
