package com.clouway.test.task2;

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
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery() {{setThreadingPolicy(new Synchroniser());}};
    Clock clock = context.mock(Clock.class);

    Server server = new Server();
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
}
