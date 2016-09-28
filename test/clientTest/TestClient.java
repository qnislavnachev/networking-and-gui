package clientTest;

import org.junit.Test;
import task2.client.Client;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
public class TestClient {

    @Test
    public void ClientReceiveMessage() throws Exception {
        Client client = new Client();
        client.join("localhost", 1111);
        String expected = client.getMessage();
        String actual = "Hello";
        assertThat(actual, is(expected));
    }
}
