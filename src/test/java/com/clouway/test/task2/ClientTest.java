package com.clouway.test.task2;

import com.clouway.task2.Client;
import com.clouway.task2.Screen;
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

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientTest {
    private  Synchroniser synchroniser = new Synchroniser();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
             setThreadingPolicy(synchroniser);
    }};
    private Screen screen = context.mock(Screen.class);

    private Client client = new Client(screen);
    private FakeServer server = new FakeServer();

    class FakeServer {
        public void start(final int port) {
            new Thread() {
                @Override
                public void run() {
                    ServerSocket server = null;
                    try {
                        server = new ServerSocket(port);
                        Socket connection = null;
                        PrintStream out = null;
                        connection = server.accept();
                        out = new PrintStream(connection.getOutputStream());
                        out.println("Date: 01.01.2016 Time: 09:24:54");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Test
    public void happyPath() throws IOException, InterruptedException {
        final States state = context.states("Waiting for connection!");
        context.checking(new Expectations() {{
            oneOf(screen).display("Date: 01.01.2016 Time: 09:24:54");
            then(state.is("Connected!"));
        }});
        server.start(6001);
        client.connect("127.0.0.1", 6001);
        synchroniser.waitUntil(state.is("Connected!"));
        assertTrue(client.getMessage().equals("Date: 01.01.2016 Time: 09:24:54"));
    }
}
