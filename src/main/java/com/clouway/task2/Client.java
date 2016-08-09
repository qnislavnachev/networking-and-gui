package com.clouway.task2;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {

    public String connect(String host, int port) throws IOException, InterruptedException {
        sleep(100);
        Socket client = new Socket(host,port);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String fromServer;

        if((fromServer = in.readLine()) != null){
            return fromServer;
        }else{
            System.out.println("Server is not online!");
        }
        client.close();

        if(client.isClosed()){
            System.out.println("Client is closed!");
        }else{
            System.out.println("Client did not close!");
        }
        return null;
    }
}
