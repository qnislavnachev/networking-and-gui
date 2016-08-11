package com.clouway;


import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ServerTest {

  class FakeUser {
    private String host;
    private Integer port;

    public FakeUser(String host, Integer port) {
      this.host = host;
      this.port = port;
    }

    public String connect() {
      try {
        Socket socket = new Socket(host, port);
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return serverInput.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
  }

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery(){{
    setThreadingPolicy(new Synchroniser());
  }};

  Clock clock = context.mock(Clock.class);
//todo
  Server server = new Server(8080, "Hello the date is", clock);
  FakeUser fakeUser=new FakeUser("",8080);

  @Test
  public void happyPath() throws Exception {
    Thread serverThread=new Thread(server);
    serverThread.start();
    context.checking(new Expectations(){{
      oneOf(clock).dateTime();
      will(returnValue("27.09.1991 09:03"));
    }});
    String expected="Hello the date is 27.09.1991 09:03";
    String actual=fakeUser.connect();

    assertThat(actual,is(expected));
  }
}