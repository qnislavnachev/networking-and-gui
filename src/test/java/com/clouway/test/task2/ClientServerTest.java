package com.clouway.test.task2;

import com.clouway.task2.TestClient;
import com.clouway.task2.TestServer;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientServerTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    TestServer server = new TestServer();
    TestClient client = new TestClient();

    @Test
    public void successfulConnectionToClient() throws IOException, InterruptedException {
        assertTrue(server.startServer(6000));
    }

    @Test
    public void successfulConnectionToServer() throws IOException, InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentServerDate = client.connect("127.0.0.1", 6001);
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        assertEquals(currentDate, currentServerDate);
    }
}
