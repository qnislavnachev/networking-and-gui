package clientTest;

import org.junit.Before;
import org.junit.Test;
import task2.client.Client;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestClient {
    private FakeServer fakeServer;
    private Client client;
    private Thread clientThread;

    @Before
    public void setUp() {
        fakeServer = new FakeServer(1111);
        client = new Client();
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.join("localhost", 1111);
            }
        });
        clientThread.start();
        fakeServer.start();
    }

    @Test
    public void ClientReceiveMessage() throws Exception {
        String actual = client.getMessage();
        String expected = "Hello";
        assertThat(actual, is(expected));
    }
}
