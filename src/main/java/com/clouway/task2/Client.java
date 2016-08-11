package com.clouway.task2;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {
    private String message;
    private Screen screen;

    public Client(Screen screen){
        this.screen = screen;
    }

    public synchronized void connect(String host,int port) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                String fromServer;
                Socket client = null;
                try {
                    client = new Socket(host, port);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    if((fromServer = in.readLine())!=null) {
                        message = fromServer;
                        screen.display(message);
                    }
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public String getMessage() throws InterruptedException {
        return message;
    }
}
