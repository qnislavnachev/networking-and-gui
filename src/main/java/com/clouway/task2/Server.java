package com.clouway.task2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Server {
    private CurrentTimeAndDate currentTimeAndDate;
    private ServerSocket server = null;
    private Socket connection = null;
    private PrintStream out = null;

    public Server(CurrentTimeAndDate currentTimeAndDate) {
        this.currentTimeAndDate = currentTimeAndDate;
    }

    public void startServer(int port) throws IOException {
        server = new ServerSocket(port);
        new Thread(){
            @Override
            public void run() {
                try {
                    while(true){
                        connection = server.accept();
                        System.out.println("Connected to:" + connection.getInetAddress().getHostName());
                        out = new PrintStream(connection.getOutputStream());
                        Date date = currentTimeAndDate.getTimeAndDate();
                        out.println(date);
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
}
