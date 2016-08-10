package com.clouway.test.task3;

import com.clouway.task3.Server;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerWithMultipleClientsTest {
    Server server = new Server();

    public void fakeClient(int port){
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket client = new Socket("127.0.0.1", port);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //if((fromServer = in.readLine())!=null)
                   // {
                    //    result = fromServer;
                   // }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Test
    public void multipleClientsConnect() throws IOException, InterruptedException {

    }
}
