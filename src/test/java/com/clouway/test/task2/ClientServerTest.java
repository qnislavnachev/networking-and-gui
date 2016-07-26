package com.clouway.test.task2;

import com.clouway.task2.Client;
import com.clouway.task2.Server;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientServerTest {
    Server server = new Server();
    Client client = new Client();

    @Test
    public void happyPath() throws IOException {
        server.startServer(6789);
        Server testServer = new Server();
        testServer.startServer(6789);
    }
}
