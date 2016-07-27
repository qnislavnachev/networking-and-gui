package com.clouway.task3;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Server {

    private ServerSocket server = null;
    private Socket connection = null;
    private BufferedReader in = null;
    private PrintStream out = null;
    private List<Socket> clients = null;


    public void startServer(int port) throws IOException {
        server = new ServerSocket(port,100);
        new Thread() {
            @Override
            public void run() {
                clients = new ArrayList();
                while(true){
                    try {
                        connectionMade();
                        sendInformation();
                        setupStreams();
                        addClient(connection);
                        sendGreeting();
                        receiveInfortmation();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void sendGreeting() throws IOException {
        out.println("Hello, you're client №" + clients.size() + " in the list!");
    }

    private void connectionMade() throws IOException {
        connection = server.accept();
        System.out.println("New client has connected from:" + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        out = new PrintStream(connection.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out.flush();
    }

    private void addClient(Socket client){
        clients.add(client);
    }

    private void sendInformation() throws IOException {
        PrintStream herald = null;
        for (Socket each: clients) {
            herald = new PrintStream(each.getOutputStream());
            herald.println("There's a new client in the list with №" + (clients.size() + 1) + " !");
        }
    }

    private void receiveInfortmation() throws IOException {
        String fromClient;
        if((fromClient = in.readLine()) != null){
            System.out.println("From client: " + fromClient);
        }
    }

}
