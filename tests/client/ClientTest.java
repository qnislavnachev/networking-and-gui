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
    private BlockingQueue<String> clientQueue;
    private BlockingQueue<String> serverQueue;

    @Before
    public void SetUp() {
        clientQueue = new LinkedBlockingQueue<>();
        serverQueue = new LinkedBlockingQueue<>();
        fakeServer = new FakeServer(1111, serverQueue);
        fakeServer.start();
        clientMessage = "Client message";
    }

    @Test
    public void ClientReceiveMessage() throws Exception {
        startClient();
        clientQueue.take();
        String actual = client.getMessage();
        String expected = "Server message";
        assertThat(actual, is(expected));
        fakeServer.shutDown();
    }

    @Test
    public void ClientSendMessage() throws Exception {
        startClient();
        serverQueue.take();
        String actual = fakeServer.getMessage();
        assertThat(actual, is(clientMessage));
        fakeServer.shutDown();
    }

    private void startClient() {
        client = new Client(new ByteArrayInputStream(clientMessage.getBytes()), clientQueue);
        client.join("localhost", 1111);
    }
}
