package com.clouway.test.task2;

import com.clouway.task2.Client;
import com.clouway.task2.ClientInt;
import com.clouway.task2.Server;
import com.clouway.task2.ServerInt;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ClientServerTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    ServerInt serverInt = context.mock(ServerInt.class);
    ClientInt clientInt = context.mock(ClientInt.class);
    Server server = new Server();
    Client client = new Client();

    @Test
    public void successfulConnection() throws IOException {

    }
}
