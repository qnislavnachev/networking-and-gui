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
    private List<Socket> clients = null;
    private Clients myClients = null;


    public void startServer(int port) throws IOException {
        server = new ServerSocket(port,100);
        clients = new ArrayList();
        myClients = new Clients(clients);
        myClients.start();
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    try {
                        connectionMade();
                        setupStreams();
                    } catch (IOException e) {
                    }
                    addClient(connection);
                }
            }
        }.start();
    }

    private void connectionMade() throws IOException {
        connection = server.accept();
        System.out.println("New client has connected from:" + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    private void addClient(Socket client){
        clients.add(client);
    }

    private void receiveInformation(Socket client) throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String fromClient;
        if((fromClient = in.readLine()) != null){
            System.out.println("From client: " + fromClient);
        }
    }

}
