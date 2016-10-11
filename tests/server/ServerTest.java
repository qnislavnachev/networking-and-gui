package server;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import task3.server.Server;

import java.io.ByteArrayInputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerTest {
    private FakeClient fakeClient;
    private FakeClient fakeClient1;
    private Server server;
    private BlockingQueue<String> queue;
    private String clientMessage;

    @Before
    public void SetUp() {
        clientMessage = "Server receive message";
        queue = new LinkedBlockingQueue<>();
        server = new Server(1111);
        server.start();
        fakeClient = new FakeClient("localhost", 1111, queue, new ByteArrayInputStream(clientMessage.getBytes()));
        fakeClient1 = new FakeClient("localhost", 1111, queue, new ByteArrayInputStream(clientMessage.getBytes()));
    }

    @Test
    public void ServerSendMessage() throws Exception {
        fakeClient.start();
        queue.take();
        String actual = fakeClient.getMessage();
        String expected = "New client detected: 1";
        assertThat(actual, is(expected));
        server.shutDown();
    }

    @Test
    public void ServerSendMessageToAll() throws Exception {
        fakeClient.start();
        fakeClient1.start();
        queue.take();
        queue.take();
        String actual1 = fakeClient.getMessage();
        String actual2 = fakeClient1.getMessage();
        String expected1 = "New client detected: 1";
        String expected2 = "New client detected: 2";
        assertThat(actual1, is(expected1));
        assertThat(actual2, is(expected2));
        server.shutDown();
    }

    @Test
    public void ServerReceiveMessage() throws Exception {
        fakeClient.start();
        queue.take();
        String actual = server.getMessage();
        assertThat(actual, is(clientMessage));
        server.shutDown();
    }
}
