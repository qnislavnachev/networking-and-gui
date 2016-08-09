package com.clouway.task3;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by borislav on 27.07.16.
 */
public class Clients extends Thread {
    private List<Socket> clients;
    private PrintStream out;
    private int size = 0;

    public Clients(List<Socket> clients){
        this.clients = clients;
    }

    @Override
    public void run() {
        while(true) {
            if (size != clients.size()) {
                try {
                    sendGreeting(clients.get((clients.size() - 1)), "Hello, you're client №" + clients.size() + " in the list!");
                    sendInformation();
                    size = clients.size();
                } catch (IOException e) {
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private void sendGreeting(Socket client, String message) throws IOException {
        out = new PrintStream(client.getOutputStream(), true);
        out.println(message);
        out.flush();
    }

    private void sendInformation() throws IOException {
        int counter = 0;
        for (Socket each: clients) {
            if(counter == (clients.size()-1)){
                break;
            }
            sendGreeting(each, "There's a new client in the list with №" + (clients.size()) + "!");
            counter++;
        }
    }
}
