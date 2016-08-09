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
    private List<Socket> clients = null;
    private Clients myClients = null;
    private Observer observer;

    public Server(Observer observer) {
        this.observer = observer;
    }

    public void startServer(int port) throws IOException, InterruptedException {
        server = new ServerSocket(port,100);
        clients = new ArrayList();
        myClients = new Clients(clients, observer);
        myClients.start();
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        connection = server.accept();
                        if(observer.clientIsAdult()) {
                            setupStreams();
                            clients.add(connection);
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }.start();
    }

    private void setupStreams() throws IOException {
        new Thread(){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            @Override
            public void run() {
                if(observer.connectionIsOptimal()) {
                    while (true) {
                        try {
                            String fromClient;
                            if ((fromClient = in.readLine()) != null) {
                                if(observer.messageIsValid()){
                                    System.out.println("From client: " + fromClient);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
}
