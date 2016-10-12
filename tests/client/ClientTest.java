package client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import task3.client.Client;

import java.io.ByteArrayInputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClientTest {
    private FakeServer fakeServer;
    private Client client;
    private String clientMessage;
    private BlockingQueue<String> queue;

    @Before
    public void setUp() throws Exception {
        queue = new LinkedBlockingQueue<>();
        fakeServer = new FakeServer(1111, queue);
        fakeServer.start();
        clientMessage = "Client message";
        startClient(1111, clientMessage);
    }

    @After
    public void tearDown() throws Exception {
        fakeServer.shutDown();
    }

    @Test
    public void clientReceiveMessage() throws Exception {
        queue.take();
        String actual = client.getMessage();
        String expected = "Server message";
        assertThat(actual, is(expected));
    }

    @Test
    public void clientSendMessage() throws Exception {
        queue.take();
        String actual = fakeServer.getMessage();
        assertThat(actual, is(clientMessage));
    }

    private void startClient(int port, String clientMessage) throws InterruptedException {
        client = new Client("localhost", port, new ByteArrayInputStream(clientMessage.getBytes()));
        client.start();
    }
}