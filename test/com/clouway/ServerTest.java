package com.clouway;

import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
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
  private class FakeClient implements Runnable {
    private String host;
    private Integer port;
    private Display display;

    public FakeClient(String host, Integer port, Display display) {
      this.host = host;
      this.port = port;
      this.display = display;
    }

    @Override
    public void run() {
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
    }
  }

  public Synchroniser synchroniser = new Synchroniser();
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery() {{
    setThreadingPolicy(synchroniser);
  }};

  private Display display = context.mock(Display.class);

  private ConnectedClients connectedClients = new ConnectedClients();
  private Server server = new Server(8080, connectedClients);
  private FakeClient fakeClient = new FakeClient("", 8080, display);
  private FakeClient fakeClient1 = new FakeClient("", 8080, display);
  private Thread serverThread = new Thread(server);
  private Thread clientThread = new Thread(fakeClient);
  private Thread clientThread1 = new Thread(fakeClient1);

  @Test
  public void oneClientConnects() throws Exception {
    final States states = context.states("connecting..");
    context.checking(new Expectations() {{
      allowing(display).show("Welcome, you are user number 1");
      then(states.is("connected"));
    }});
    serverThread.start();
    clientThread.start();
    synchroniser.waitUntil(states.is("connected"));
    server.shutdownServer();
  }

  @Test
  public void moreThanOneClientsConnectToTheServer() throws Exception {
    final States states = context.states("connecting..");
    context.checking(new Expectations() {{
      allowing(display).show("Welcome, you are user number 1");
      allowing(display).show("Welcome, you are user number 2");
      allowing(display).show("A client connected to the server.");
      then(states.is("connected"));
    }});
    serverThread.start();
    clientThread.start();
    clientThread1.start();
    synchroniser.waitUntil(states.is("connected"));
    server.shutdownServer();
  }
}
