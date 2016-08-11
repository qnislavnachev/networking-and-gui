package com.clouway.test.task3;

import com.clouway.task3.Server;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerWithMultipleClientsTest {
    Server server = new Server();
    FakeClient one = new FakeClient();
    FakeClient two = new FakeClient();

    class FakeClient {
        private String message;
        public synchronized void connect(String host,int port) throws InterruptedException {
            new Thread(){
                @Override
                public void run() {
                    String fromServer;
                    Socket client = null;
                    try {
                        client = new Socket(host, port);
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        if((fromServer = in.readLine())!=null)
                        {
                            message = fromServer;
                        }

                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        public boolean hasReceivedMessage(String expectedMessage) throws InterruptedException {
            while (message == null){
                sleep(10);
            }
            if(message.equals(expectedMessage)){
                return true;
            }else{
                return false;
            }
        }
    }
}
