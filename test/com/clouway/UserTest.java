package com.clouway;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class UserTest {

  class FakeServer implements Runnable {
    private Integer port;

    public FakeServer(Integer port) {
      this.port = port;
    }

    @Override
    public void run() {
      ServerSocket serverSocket = null;
      PrintWriter output = null;
      try {
        serverSocket = new ServerSocket(port);
        while (true) {
          Socket clientSocket = serverSocket.accept();
          output = new PrintWriter(clientSocket.getOutputStream(), true);
          output.println("Hello the time is 27.09.1991 09:03");
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          serverSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        output.close();
      }

    }
  }

  JUnitRuleMockery context = new JUnitRuleMockery() {{
    setThreadingPolicy(new Synchroniser());
  }};

  private Display display = context.mock(Display.class);

  private User user = new User("", 8000, display);
  private FakeServer fakeServer = new FakeServer(8000);

  @Test
  public void happyPath() throws Exception {
    Thread serverThread = new Thread(fakeServer);

    context.checking(new Expectations() {{
      oneOf(display).show("Hello the time is 27.09.1991 09:03");
    }});

    serverThread.start();
    user.connectToServer();
  }
}
