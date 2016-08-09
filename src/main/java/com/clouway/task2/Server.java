package com.clouway.task2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Server {
    private Clock clock;
    private ServerSocket server = null;
    private Socket connection = null;
    private PrintStream out = null;

    public void startServer(int port, Clock clock) throws IOException {
        this.clock = clock;
        server = new ServerSocket(port);
        new Thread(){
            @Override
            public void run() {
                try {
                    while(true){
                        connection = server.accept();
                        System.out.println("Connected to:" + connection.getInetAddress().getHostName());
                        out = new PrintStream(connection.getOutputStream());

                        sendGreeting();
                        connection.close();
                        if(connection.isClosed()){
                            System.out.println("Client is offline!");
                        }else{
                            System.out.println("Client is online!");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void sendGreeting() throws IOException {
        String date = clock.getDate();
        String time = clock.getTime();
        out.println("Date: " + date + " Time: " + time);
    }
}
