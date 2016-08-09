package com.clouway.test.task2;

import com.clouway.task2.Client;
import com.clouway.task2.Clock;
import com.clouway.task2.Server;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientServerTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery() {{setThreadingPolicy(new Synchroniser());}};
    Clock clock = context.mock(Clock.class);

    Server server = new Server();
    Client client = new Client();
    String fromServer, result;

    public void fakeClient(int port) throws IOException, InterruptedException {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Socket client = new Socket("127.0.0.1", port);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    if((fromServer = in.readLine())!=null)
                    {
                        result = fromServer;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

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
    public void sendTimeAndDateToClient() throws IOException, InterruptedException {
        context.checking(new Expectations(){{
            oneOf(clock).getDate();
            will(returnValue("01.01.2016"));

            oneOf(clock).getTime();
            will(returnValue("09:24:54"));
        }});

        fakeClient(6000);
        server.startServer(6000, clock);
        sleep(1000);
        assertTrue(result.equals("Date: 01.01.2016 Time: 09:24:54"));
    }

    @Test
    public void getTimeAndDateFromServer() throws IOException {
        fakeServer(6001);
        result = client.connect("127.0.0.1", 6001);

        assertTrue(result.equals("Date: 01.01.2016 Time: 09:24:54"));
    }
}
