package com.clouway.task2;

import java.io.IOException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientDemo {

    public static void main(String[] args){
        Client client = new Client();
        try {
            client.connect("127.0.0.1", 6790);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
