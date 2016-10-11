package client;

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
    public void SetUp() {
        queue = new LinkedBlockingQueue<>();
        fakeServer = new FakeServer(1111, queue);
        fakeServer.start();
        clientMessage = "Client message";
    }

    @Test
    public void ClientReceiveMessage() throws Exception {
        startClient();
        queue.take();
        String actual = client.getMessage();
        String expected = "Server message";
        assertThat(actual, is(expected));
        fakeServer.shutDown();
    }

    @Test
    public void ClientSendMessage() throws Exception {
        startClient();
        queue.take();
        String actual = fakeServer.getMessage();
        assertThat(actual, is(clientMessage));
        fakeServer.shutDown();
    }

    private void startClient() throws InterruptedException {
        client = new Client(new ByteArrayInputStream(clientMessage.getBytes()));
        client.join("localhost", 1111);
    }
}