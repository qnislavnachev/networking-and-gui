package serverTest;

import org.junit.Test;
import task2.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestServer {

    @Test
    public void serverSendMessage() throws Exception {
        Socket fakeClient = new Socket("localhost", 1111);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fakeClient.getInputStream()));
        String expected = reader.readLine();
        String actual = "Hello from the server ! Wed Sep 28 12:07:12 EEST 2016";
        assertThat(actual, is(expected));
    }

}
