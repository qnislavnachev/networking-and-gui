package serverTest;

import org.junit.Before;
import org.junit.Test;
import task2.server.Server;
import task2.server.CurrentDate;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestServer {
    private FakeClient fakeClient;
    private Server server;
    private Thread serverThread;
    private CurrentDate currentDate;

    @Before
    public void setUp() {
        currentDate = new CurrentDate() {
            private Date date = new Date();

            @Override
            public Date getCurrentDate() {
                return date;
            }
        };
        server = new Server(1111, currentDate);
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
        String expected = "Hello from the server ! " + currentDate.getCurrentDate();
        assertThat(actual, is(expected));
    }
}