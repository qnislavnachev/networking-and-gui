package com.clouway.multiClientServer;

import com.clouway.multiClientServer.server.Server;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class ServerTest {

  class FakeClient {
    public String msg;

    public void connect() {
      try {
        Socket socket = new Socket("localhost", 4444);
        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              while (true) {
                if (input.read() == -1) {
                  break;
                }
              }

              input.close();
              socket.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }).start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public Synchroniser synchroniser = new Synchroniser();

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery() {{
    setThreadingPolicy(synchroniser);
  }};

  private Display myDisplay = context.mock(Display.class);
  private Server server = new Server(4444, myDisplay);

  @Before
  public void setUp() {
    server.openConnection();
  }

  @After
  public void tearDown() throws Exception {
    server.shutDown();
  }

  @Test
  public void happyPath() throws Exception {
    FakeClient fakeClient = new FakeClient();
    final States states = context.states("connecting");
    context.checking(new Expectations() {{
      oneOf(myDisplay).show("Client 1 connected.");
      then(states.is("connected"));
    }});
    fakeClient.connect();
    synchroniser.waitUntil(states.is("connected"));
  }

  @Test
  public void multipleConnections() throws Exception {
    final States states = context.states("connecting");
    FakeClient fakeClient1 = new FakeClient();
    FakeClient fakeClient2 = new FakeClient();

    context.checking(new Expectations() {{
      oneOf(myDisplay).show("Client 1 connected.");
      oneOf(myDisplay).show("Client 2 connected.");
      then(states.is("connected"));
    }});
    fakeClient1.connect();
    fakeClient2.connect();

    synchroniser.waitUntil(states.is("connected"));
  }
}
