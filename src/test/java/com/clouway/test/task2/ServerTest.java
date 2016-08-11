package com.clouway.test.task2;

import com.clouway.task2.Clock;
import com.clouway.task2.Screen;
import com.clouway.task2.Server;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerTest {
    private Synchroniser synchroniser = new Synchroniser();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private Clock clock = context.mock(Clock.class);
    private Screen screen = context.mock(Screen.class);

    private Server server = new Server(clock, screen);
    private FakeClient client = new FakeClient(screen);

    class FakeClient {
        private String message;
        private Screen screen;

        public FakeClient(Screen screen) {
            this.screen = screen;
        }

        public void connect(String host, int port) throws InterruptedException {
            new Thread() {
                @Override
                public void run() {
                    String fromServer;
                    Socket client = null;
                    try {
                        client = new Socket(host, port);
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        if ((fromServer = in.readLine()) != null) {
                            message = fromServer;
                            screen.display("Time and date received!");
                        }
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        public String getMessage() throws InterruptedException {
            return message;
        }
    }

    @Test
    public void happyPath() throws IOException, InterruptedException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");
        Date date = dateFormat.parse("01.01.2016 09:24:54");

        States connecting = context.states("Waiting for connection!");
        context.checking(new Expectations() {{
            oneOf(clock).getTimeAndDate();
            will(returnValue(date));
            oneOf(screen).display("Time and date send!");
            oneOf(screen).display("Time and date received!");
            then(connecting.is("Information received!"));
        }});
        server.start(6000);
        client.connect("127.0.0.1", 6000);
        synchroniser.waitUntil(connecting.is("Information received!"));
        assertTrue(client.getMessage().equals("Fri Jan 01 09:24:54 EET 2016"));
    }
}
