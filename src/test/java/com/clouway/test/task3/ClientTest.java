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

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientTest {
    Synchroniser synchroniser = new Synchroniser();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(synchroniser);
    }};
    Screen screen = context.mock(Screen.class);
    FakeServer server = new FakeServer();
    Client firstClient = new Client(screen);
    Client secondClient = new Client(screen);

    class FakeServer {
        private List<Socket> clients = new ArrayList();
        private Socket connection = null;

        public void start(int port) throws IOException {
            ServerSocket server = new ServerSocket(port);
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            connection = server.accept();
                            clients.add(connection);
                            sendMessages();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        private void sendMessages() throws IOException {
            PrintStream out = new PrintStream(clients.get((clients.size() - 1)).getOutputStream());
            out.println("Hello, you're client number №" + (clients.size()));
            for (int i = 0; i < (clients.size() - 1); i++) {
                out = new PrintStream(clients.get(i).getOutputStream());
                out.println("There's a new client in the list with №" + (clients.size()));
            }
        }
    }

    @Test
    public void happyPath() throws IOException, NoSocketException, InterruptedException {
        final States state = context.states("Connecting...");
        context.checking(new Expectations() {{
            oneOf(screen).display("Connection established!");
            then(state.is("First client connected!"));
            oneOf(screen).display("Connection established!");
            then(state.is("Second client connected!"));
        }});
        server.start(6000);
        firstClient.connect("127.0.0.1", 6000);
        synchroniser.waitUntil(state.is("First client connected!"));
        secondClient.connect("127.0.0.1", 6000);
        synchroniser.waitUntil(state.is("Second client connected!"));
    }
}
