package server;

import org.junit.After;
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
    public void setUp() {
        clientMessage = "Server receive message";
        queue = new LinkedBlockingQueue<>();
        server = new Server(1111);
        server.start();
        fakeClient = new FakeClient("localhost", 1111, queue, new ByteArrayInputStream(clientMessage.getBytes()));
        fakeClient1 = new FakeClient("localhost", 1111, queue, new ByteArrayInputStream(clientMessage.getBytes()));
        fakeClient.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutDown();
    }

    @Test
    public void serverSendMessage() throws Exception {
        queue.take();
        String actual = fakeClient.getMessage();
        String expected = "New client detected: 1";
        assertThat(actual, is(expected));
    }

    @Test
    public void serverSendMessageToAll() throws Exception {
        fakeClient1.start();
        queue.take();
        queue.take();
        String actual1 = fakeClient.getMessage();
        String actual2 = fakeClient1.getMessage();
        String expected1 = "New client detected: 1";
        String expected2 = "New client detected: 2";
        assertThat(actual1, is(expected1));
        assertThat(actual2, is(expected2));
    }

    @Test
    public void serverReceiveMessage() throws Exception {
        queue.take();
        String actual = server.getMessage();
        assertThat(actual, is(clientMessage));
    }
}