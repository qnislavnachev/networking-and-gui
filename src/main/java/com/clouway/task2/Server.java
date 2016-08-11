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
    private ServerSocket server = null;
    private Socket connection = null;
    private PrintStream out = null;
    private Screen screen;

    public Server(Clock clock, Screen screen) {
        this.clock = clock;
        this.screen = screen;
    }

    public void startServer(int port) throws IOException {
        server = new ServerSocket(port);
        new Thread(){
            @Override
            public void run() {
                try {
                    connection = server.accept();
                    out = new PrintStream(connection.getOutputStream());
                    Date date = clock.getTimeAndDate();
                    out.println(date);
                    screen.display("Time and date send!");
                    connection.close();
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
