package com.clouway.test.task3;

import com.clouway.task3.NoSocketException;
import com.clouway.task3.Screen;
import com.clouway.task3.Server;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerWithMultipleClientsTest {
    private Synchroniser synchroniser = new Synchroniser();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private Screen screen = context.mock(Screen.class);
    private Server server = new Server(screen);
    private FakeClient clientOne = new FakeClient();
    private FakeClient clientTwo = new FakeClient();
    private FakeClient clientThree = new FakeClient();

    class FakeClient {

        public synchronized void connect(final String host, final int port) throws InterruptedException {
            new Thread() {
                @Override
                public void run() {
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
    public void happyPathWithOneClient() throws IOException, InterruptedException, NoSocketException {
        final States connecting = context.states("Waiting for connection!");
        context.checking(new Expectations() {{
            oneOf(screen).display("Hello, you're client №1 in the list!");
            then(connecting.is("Successful connection!"));
        }});
        server.start(6003);
        clientOne.connect("127.0.0.1", 6003);
        synchroniser.waitUntil(connecting.is("Successful connection!"));
    }

    @Test
    public void happyPathWithSeveralClients() throws IOException, InterruptedException, NoSocketException {
        final States connecting = context.states("Waiting for connection!");
        context.checking(new Expectations() {{
            oneOf(screen).display("Hello, you're client №1 in the list!");
            oneOf(screen).display("Hello, you're client №2 in the list!");
            oneOf(screen).display("Hello, you're client №3 in the list!");
            then(connecting.is("Successful connection!"));
        }});
        server.start(6002);
        clientOne.connect("127.0.0.1", 6002);
        clientTwo.connect("127.0.0.1", 6002);
        clientThree.connect("127.0.0.1", 6002);
        synchroniser.waitUntil(connecting.is("Successful connection!"));
    }

    @Test
    public void closingServer() throws InterruptedException, IOException, NoSocketException {
        final States connecting = context.states("Waiting for connection!");
        context.checking(new Expectations() {{
            oneOf(screen).display("Hello, you're client №1 in the list!");
            then(connecting.is("Successful connection!"));
            oneOf(screen).display("Connection to clients is closed!");
            oneOf(screen).display("Stream to clients is closed!");
            oneOf(screen).display("Server is closed!");
            then(connecting.is("Server is offline!"));
        }});
        server.start(6004);
        clientOne.connect("127.0.0.1", 6004);
        synchroniser.waitUntil(connecting.is("Successful connection!"));
        server.stop();
        synchroniser.waitUntil(connecting.is("Server is offline!"));
    }
}
