package com.clouway.task2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Server {

    private ServerSocket server = null;
    private Socket connection = null;
    private PrintStream out = null;


    public void startServer(int port) throws IOException {
        server = new ServerSocket(port);
        while(true){
            connectionMade();
            setupStreams();
            sendGreeting();
            System.out.println("Client has disconnected!");
        }
    }

    private void sendGreeting() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        out.println("Hello! " + dateFormat.format(date));
    }

    private void connectionMade() throws IOException {
        System.out.println("Waiting for connection ...");
        connection = server.accept();
        System.out.println("Connected to:" + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        out = new PrintStream(connection.getOutputStream());
        out.flush();
    }

}
