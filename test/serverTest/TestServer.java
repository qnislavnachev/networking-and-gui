package serverTest;

import org.junit.Before;
import org.junit.Test;
import task2.server.Server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestServer {
    private FakeClient fakeClient;
    private Server server;
    private Thread clientThread;
    @Before
    public void setUp(){
        fakeClient = new FakeClient();
        server = new Server(1111);
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                fakeClient.connect("localhost", 1111);
            }
        });
        clientThread.start();
        server.start();
    }

    @Test
    public void serverSendMessage() throws Exception {
        String actual = fakeClient.getMessage();
        String expected = "Hello from the server ! Wed Sep 28 17:09:46 EEST 2016";
        assertThat(actual, is(expected));
    }
}