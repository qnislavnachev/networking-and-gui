package com.clouway.task3;

import java.io.IOException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientDemo {

    public static void main(String[] args){
        Client client = new Client();
        try {
            client.connect("127.0.0.1", 6791);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSocketException e) {
            e.printStackTrace();
        }
    }
}
