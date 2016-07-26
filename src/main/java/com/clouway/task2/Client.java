package com.clouway.task2;

import java.io.*;
import java.net.Socket;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {

    public void connect(String host, int port) throws IOException {
        Socket client = new Socket(host,port);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String fromServer;

        while((fromServer = in.readLine()) != null){
            System.out.println("From server: " + fromServer);
        }
    }

}
