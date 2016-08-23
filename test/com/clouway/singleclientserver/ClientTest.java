package com.clouway.singleclientserver;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ClientTest {

  class FakeServer implements Runnable {
    private Integer port;
    private String message;

    public FakeServer(Integer port, String message) {
      this.port = port;
      this.message = message;
    }

    @Override
    public void run() {
      try (ServerSocket serverSocket = new ServerSocket(port)) {
        while (true) {
          Socket clientSocket = serverSocket.accept();
          PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
          output.println(message);
          output.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery() {{
    setThreadingPolicy(new Synchroniser());
  }};

  private Display display = context.mock(Display.class);
  private Client client = new Client("", 8000, display);
  private FakeServer fakeServer = new FakeServer(8000, "Hello the time is 27.09.1991 09:03");

  @Test
  public void happyPath() throws Exception {
    Thread serverThread = new Thread(fakeServer);
    context.checking(new Expectations() {{
      oneOf(display).show("Hello the time is 27.09.1991 09:03");
    }});
    serverThread.start();
    client.connect();
  }
}
