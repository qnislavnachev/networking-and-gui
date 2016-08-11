package com.clouway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class User implements Runnable {
  private String host;
  private Integer port;
  private Response response;

  public User(String host, Integer port, Response response) {
    this.host = host;
    this.port = port;
    this.response = response;
  }

  public void connectToServer() {
    try {
      Socket socket=new Socket(host,port);
      BufferedReader serverInput=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      serverInput.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {

  }
}
