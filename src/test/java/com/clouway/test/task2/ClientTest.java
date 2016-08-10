package com.clouway.test.task2;

import com.clouway.task2.Client;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientTest {
    Client client = new Client();
    String result;

    public void fakeServer(int port) throws IOException {
        new Thread() {
            @Override
            public void run() {
                ServerSocket server = null;
                try {
                    server = new ServerSocket(port);
                    Socket connection = null;
                    PrintStream out = null;
                    connection = server.accept();
                    out = new PrintStream(connection.getOutputStream()
                    );
                    out.println("Date: 01.01.2016 Time: 09:24:54");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Test
    public void getTimeAndDateFromServer() throws IOException, InterruptedException {
        fakeServer(6001);
        result = client.connect("127.0.0.1", 6001);

        assertTrue(result.equals("Date: 01.01.2016 Time: 09:24:54"));
    }
}
