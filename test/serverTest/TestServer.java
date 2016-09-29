package serverTest;

import org.junit.Before;
import org.junit.Test;
import task2.server.Server;
import task2.server.ServerDate;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestServer {
    private FakeClient fakeClient;
    private Server server;
    private Thread serverThread;
    private ServerDate serverDate;

    @Before
    public void setUp() {
        serverDate = new ServerDate() {
            private Date date = new Date();

            @Override
            public Date getServerDate() {
                return date;
            }
        };
        server = new Server(1111, serverDate);
        fakeClient = new FakeClient();
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start();
            }
        });
        serverThread.start();
    }

    @Test
    public void serverSendMessage() throws Exception {
        Thread.sleep(1000);
        fakeClient.connect("localhost", 1111);
        String actual = fakeClient.getMessage();
        String expected = "Hello from the server ! " + serverDate.getServerDate();
        assertThat(actual, is(expected));
    }
}