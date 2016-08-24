package com.clouway.multiclientserver;

import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ServerTest {
  private class FakeClient {
    private String host;
    private Integer port;
    private Display display;

    public FakeClient(String host, Integer port, Display display) {
      this.host = host;
      this.port = port;
      this.display = display;
    }

    public void connect() {
      new Thread(() -> {
        try (Socket socket = new Socket(host, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
          while (true) {
            display.show(input.readLine());
          }
        } catch (UnknownHostException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();
    }
  }

  public Synchroniser synchroniser = new Synchroniser();
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery() {{
    setThreadingPolicy(synchroniser);
  }};

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  private Display display = context.mock(Display.class);
  private ConnectedClients connectedClients = new ConnectedClients();
  private Server server = new Server(8080, connectedClients);
  private FakeClient fakeClient = new FakeClient("", 8080, display);
  private FakeClient fakeClient1 = new FakeClient("", 8080, display);

  @Test
  public void happyPath() throws Exception {
    final States states = context.states("connecting..");
    context.checking(new Expectations() {{
      oneOf(display).show("Welcome, you are user number 1");
      then(states.is("connected"));
    }});
    server.start();
    fakeClient.connect();
    synchroniser.waitUntil(states.is("connected"));
  }

  @Test
  public void multipleConnections() throws Exception {
    final States states = context.states("connecting..");
    context.checking(new Expectations() {{
      oneOf(display).show("Client 1 connected to the server.");
      oneOf(display).show("Welcome, you are user number 1");
      oneOf(display).show("Client 2 connected to the server.");
      oneOf(display).show("Welcome, you are user number 2");
      then(states.is("connected"));
    }});
    server.start();
    fakeClient.connect();
    fakeClient1.connect();
    synchroniser.waitUntil(states.is("connected"));
  }
}
