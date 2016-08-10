package com.clouway.multiClientServer;

import com.clouway.multiClientServer.client.Client;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class ClientTest {

  class FakeServer {
    private int clientNumber = 0;
    public void openConnection() {
      new Thread(new Runnable() {
        @Override
        public void run() {
          try (ServerSocket server = new ServerSocket(4444)) {
            while (true) {
              try {
                Socket connection = server.accept();
                clientNumber++;
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                out.println("Client " + clientNumber + " connected.");
                out.flush();
              } catch (IOException ex) {
              }
            }
          } catch (IOException ex) {
          }
        }
      }).start();
    }
  }

  public Synchroniser synchroniser = new Synchroniser();

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery() {{
    setThreadingPolicy(synchroniser);
  }};

  private Display myDisplay = context.mock(Display.class);
  private FakeServer fakeServer = new FakeServer();
  private Client client = new Client("localhost", 4444, myDisplay);

  @Before
  public void setUp() {
    fakeServer.openConnection();
  }

  @Test
  public void happyPath() throws Exception {
    final States states = context.states("connecting..");
    context.checking(new Expectations() {{
      oneOf(myDisplay).show("Client 1 connected.");
      then(states.is("connected"));
    }});
    fakeServer.openConnection();
    client.connect();
    synchroniser.waitUntil(states.is("connected"));
  }

  @Test
  public void multipleConnections() throws Exception {
    final States states = context.states("connecting..");
    context.checking(new Expectations() {{
      oneOf(myDisplay).show("Client 1 connected.");
      oneOf(myDisplay).show("Client 2 connected.");
      oneOf(myDisplay).show("Client 3 connected.");
      then(states.is("connected"));
    }});
    fakeServer.openConnection();
    client.connect();
    client.connect();
    client.connect();
    synchroniser.waitUntil(states.is("connected"));
  }
}