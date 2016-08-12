package com.clouway.task3;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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
                while (true) {
                    try {
                        hasReceivedMessage();
                    } catch (IOException e) {
                    } catch (NoSocketException e) {
                        System.out.println("Server is offline!");
                    }
                }
            }
        }.start();
        /*new Thread() {
            @Override
            public void run() {
                while (true) {
                    writeToServer();
                }
            }
        }.start();*/
    }

    private void hasReceivedMessage() throws IOException, NoSocketException {
            String fromServer;
            if ((fromServer = in.readLine()) != null) {
                screen.display(fromServer);
            }
    }

    /*private void writeToServer() {
        String toServer;
        toServer = sc.nextLine();
        out.println(toServer);
    }*/

}
