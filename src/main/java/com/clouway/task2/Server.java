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

    public Server(Clock clock) {
        this.clock = clock;
    }

    public void startServer(int port) throws IOException {
        server = new ServerSocket(port);
        new Thread(){
            @Override
            public void run() {
                try {
                    connection = server.accept();
                    System.out.println("Connected to:" + connection.getInetAddress().getHostName());
                    out = new PrintStream(connection.getOutputStream());
                    Date date = clock.getTimeAndDate();
                    out.println(date);
                    connection.close();
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
