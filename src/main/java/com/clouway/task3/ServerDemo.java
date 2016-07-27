package com.clouway.task3;

import java.io.IOException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerDemo {

    public static void main(String[] args){
        Server server = new Server();
        try {
            server.startServer(6789);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
