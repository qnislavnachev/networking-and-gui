package com.clouway.task3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {

    private Socket client = null;
    private BufferedReader in = null;
    private PrintStream out = null;
    private String fromServer, toServer;
    private Scanner sc = null;

    public void connect(String host, int port) throws IOException {
        client = new Socket(host,port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        sc = new Scanner(System.in);
        out = new PrintStream(client.getOutputStream(), true);

        while(true){
            readFromServer();
            writeToServer();
        }
    }

    private void readFromServer() throws IOException {
        if((fromServer = in.readLine()) != null) {
            System.out.println("From server: " + fromServer);
        }
    }

    private void writeToServer(){
        if(sc.hasNextLine()){
            toServer = sc.nextLine();
            out.println(toServer);
        }
    }

}
