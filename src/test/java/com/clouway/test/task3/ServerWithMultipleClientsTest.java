package com.clouway.test.task3;

import com.clouway.task3.Observer;
import com.clouway.task3.Server;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ServerWithMultipleClientsTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery() {{setThreadingPolicy(new Synchroniser());}};
    Observer observer = context.mock(Observer.class);
    Server server = new Server(observer);

    public void fakeClient(int port) throws IOException, InterruptedException {
        sleep(2000);
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket client = new Socket("127.0.0.1", port);
                    PrintStream out = new PrintStream(client.getOutputStream(), true);
                    out.println("Hello, I'm client!");
                } catch (IOException e) {
                }
            }
        }.start();
    }

    @Test
    public void multipleClientsConnect() throws IOException, InterruptedException {
        context.checking(new Expectations(){{
            oneOf(observer).clientIsAdult();
            will(returnValue(true));
            oneOf(observer).connectionIsOptimal();
            will(returnValue(true));
            oneOf(observer).messageIsValid();
            will(returnValue(true));
            oneOf(observer).isClientGreeted();
            will(returnValue(true));
            oneOf(observer).isClientInformed();
            will(returnValue(true));
        }});
        fakeClient(6000);
        fakeClient(6000);
        server.startServer(6000);
    }
}
