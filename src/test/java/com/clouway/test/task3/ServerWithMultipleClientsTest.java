package com.clouway.test.task3;

import com.clouway.task3.Screen;
import com.clouway.task3.Server;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
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
    Synchroniser synchroniser = new Synchroniser();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(synchroniser);
    }};
    Screen screen = context.mock(Screen.class);
    Server server = new Server(screen);
    FakeClient clientOne = new FakeClient();
    FakeClient clientTwo = new FakeClient();
    FakeClient clientThree = new FakeClient();

    class FakeClient {
        private String message;

        public synchronized void connect(String host, int port) throws InterruptedException {
            new Thread() {
                @Override
                public void run() {
                    String fromServer;
                    Socket client = null;
                    try {
                        client = new Socket(host, port);
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Test
    public void happyPath() throws IOException, InterruptedException {
        final States connecting = context.states("Waiting for connection!");
        context.checking(new Expectations() {{
            oneOf(screen).display("Client has connected!");
            oneOf(screen).display("Client has connected!");
            oneOf(screen).display("Client has connected!");
            then(connecting.is("Successful connection!"));
        }});
        server.startServer(6000);
        clientOne.connect("127.0.0.1", 6000);
        clientTwo.connect("127.0.0.1", 6000);
        clientThree.connect("127.0.0.1", 6000);
        synchroniser.waitUntil(connecting.is("Successful connection!"));
    }
}
