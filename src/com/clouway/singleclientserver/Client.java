package com.clouway.singleclientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Client {
  private String host;
  private Integer port;
  private Display display;


  public Client(String host, Integer port, Display display) {
    this.host = host;
    this.port = port;
    this.display = display;
  }
  
  public void connect() {
    try {
      Socket socket = new Socket(host, port);
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      display.show(input.readLine());
      input.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
