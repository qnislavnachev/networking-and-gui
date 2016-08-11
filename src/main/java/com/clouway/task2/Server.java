package com.clouway.task2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Server {
    private Clock clock;
    private Socket connection = null;
    private Screen screen;

    public Server(Clock clock, Screen screen) {
        this.clock = clock;
        this.screen = screen;
    }

    public void start(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        new Thread() {
            @Override
            public void run() {
                try {
                    connection = server.accept();
                    PrintStream out = new PrintStream(connection.getOutputStream());
                    Date date = clock.getTimeAndDate();
                    out.println(date);
                    screen.display("Time and date send!");
                    out.close();
                    connection.close();
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
