package com.clouway.task3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {
    private BufferedReader in = null;
    private PrintStream out = null;
    private Scanner sc = new Scanner(System.in);
    private Screen screen;

    public Client(Screen screen) {
        this.screen = screen;
    }

    public void connect(String host, int port) throws IOException, NoSocketException {
        Socket client = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintStream(client.getOutputStream(), true);
        screen.display("Connection established!");

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        hasReceivedMessage();
                    } catch (IOException e) {
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
        new Thread() {
            @Override
            public void run() {
                try {
                    while (in.read() > (-1)) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    throw new NoSocketException();
                } catch (NoSocketException e) {
                    System.out.println("Server is offline");
                }
            }
        }.start();
    }

    public void hasReceivedMessage() throws IOException {
        String fromServer;
        if ((fromServer = in.readLine()) != null) {
            System.out.println("From server: " + fromServer);
        }
    }

    /*private void writeToServer() {
        String toServer;
        toServer = sc.nextLine();
        out.println(toServer);
    }*/

}
