package com.clouway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class User {
  private String host;
  private Integer port;
  private Display display;

  public User(String host, Integer port, Display display) {
    this.host = host;
    this.port = port;
    this.display = display;
  }

  public void connectToServer() {
    try {
      Socket socket=new Socket(host,port);

      BufferedReader serverInput=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      display.show(serverInput.readLine());

      serverInput.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
