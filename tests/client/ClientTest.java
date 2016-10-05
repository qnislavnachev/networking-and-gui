package client;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import task3.client.Client;

public class ClientTest {
    private FakeServer fakeServer;
    private Thread clientThread;
    private Client client;

    @Before
    public void SetUp() {
        fakeServer = new FakeServer(1111);
        client = new Client();
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.join("localhost", 1111);
            }
        });
        fakeServer.start();
    }

    @Test
    public void ClientReceiveMessage() throws Exception {
        clientThread.start();
        Thread.sleep(1000);
        String expected = "Server message";
        String actual = client.getMessage();
        assertThat(actual, is(expected));
        fakeServer.shutDown();
    }
}
