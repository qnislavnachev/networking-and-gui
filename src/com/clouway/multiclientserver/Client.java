package com.clouway.multiclientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Client implements Runnable {
  private String host;
  private Integer port;
  private Display display;


  public Client(String host, Integer port, Display display) {
    this.host = host;
    this.port = port;
    this.display = display;
  }

  /**
   * Connects to the server and waits for response from it.
   */
  @Override
  public void run() {
    try (Socket socket = new Socket(host, port);
         BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))
    ) {
      while (true) {
        display.show(input.readLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
