package com.clouway.test.task3;

import com.clouway.task3.Client;
import com.clouway.task3.NoSocketException;
import com.clouway.task3.Screen;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientTest {
    private Synchroniser synchroniser = new Synchroniser();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private Screen screen = context.mock(Screen.class);
    private FakeServer server = new FakeServer();
    private Client firstClient = new Client(screen);
    private Client secondClient = new Client(screen);

    class FakeServer {

        private List<Socket> clients = new ArrayList();
        private Socket connection;
        private ServerSocket server;
        private boolean isUp = true;
        private PrintStream out = null;

        public void start(int port) throws IOException {
            server = new ServerSocket(port);
            new Thread() {
                @Override
                public void run() {
                    while (isUp) {
                        try {
                            connection = server.accept();
                            clients.add(connection);
                            sendMessages();
                        } catch (IOException e) {
                        }
                    }
                }
            }.start();
        }

        public void stop() throws IOException {
            for (int i = 0; i < (clients.size() - 1); i++) {
                clients.get(i).close();
            }
            connection.close();
            server.close();
            isUp = false;
        }

        private void sendMessages() throws IOException {
            out = new PrintStream(clients.get((clients.size() - 1)).getOutputStream());
            out.println("Hello, you're client number №" + (clients.size()));
            for (int i = 0; i < (clients.size() - 1); i++) {
                out = new PrintStream(clients.get(i).getOutputStream());
                out.println("There's a new client in the list with №" + (clients.size()));
            }
        }
    }

    @Test
    public void happyPath() throws IOException, InterruptedException {
        final States state = context.states("Connecting...");
        context.checking(new Expectations() {{
            oneOf(screen).display("Hello, you're client number №1");
            then(state.is("First client connected!"));
        }});
        server.start(6001);
        firstClient.connect("127.0.0.1", 6001);
        synchroniser.waitUntil(state.is("First client connected!"));
    }

    @Test
    public void multipleConnections() throws IOException, InterruptedException {
        final States state = context.states("Connecting...");
        context.checking(new Expectations() {{
            oneOf(screen).display("Hello, you're client number №1");
            then(state.is("First client connected!"));
            oneOf(screen).display("There's a new client in the list with №2");
            oneOf(screen).display("Hello, you're client number №2");
            then(state.is("Second client connected!"));
        }});
        server.start(6000);
        firstClient.connect("127.0.0.1", 6000);
        synchroniser.waitUntil(state.is("First client connected!"));
        secondClient.connect("127.0.0.1", 6000);
        synchroniser.waitUntil(state.is("Second client connected!"));
    }

    @Test
    public void serverStop() throws IOException, InterruptedException {
        FakeServer server = new FakeServer();
        Client client = new Client(screen);
        final States state = context.states("Connecting...");
        context.checking(new Expectations() {{
            oneOf(screen).display("Hello, you're client number №1");
            oneOf(screen).display("Server is offline!");
            then(state.is("Error, server will stop now"));
        }});
        server.start(6005);
        client.connect("127.0.0.1", 6005);
        server.stop();
        synchroniser.waitUntil(state.is("Error, server will stop now"));
    }
}
