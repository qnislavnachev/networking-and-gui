package com.clouway.task3;

import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerDemo {

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        try {
            server.startServer(6791);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
