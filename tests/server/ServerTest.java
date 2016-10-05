package server;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import task3.server.Server;

public class ServerTest {
    private FakeClient fakeClient;
    private FakeClient fakeClient1;
    private Server server;

    @Before
    public void SetUp() {
        server = new Server(1111);
        fakeClient = new FakeClient("localhost", 1111);
        fakeClient1 = new FakeClient("localhost", 1111);
        server.start();
    }

    @Test
    public void ServerSendMessage() throws Exception {
        fakeClient.start();
        Thread.sleep(1000);
        String actual = fakeClient.getMessage();
        String expected = "New client detected: 1";
        assertThat(actual, is(expected));
        server.shutDown();
    }

    @Test
    public void ServerSendMessageToAll() throws Exception {
        fakeClient.start();
        Thread.sleep(1000);
        fakeClient1.start();
        Thread.sleep(1000);
        String actual1 = fakeClient.getMessage();
        String actual2 = fakeClient1.getMessage();
        String expected =  "New client detected: 2";
        assertThat(actual1, is(expected));
        assertThat(actual2, is(expected));
    }
}
