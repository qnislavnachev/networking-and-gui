package com.clouway.multiClientServer.client;

import com.clouway.multiClientServer.Display;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class Client {
  private final String host;
  private final Integer port;
  private Display display;

  public Client(String host, Integer port, Display display) {
    this.host = host;
    this.port = port;
    this.display = display;
  }

  public String connect() {

    try {
      Socket socket = new Socket(host, port);
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;

            while (true) {
              if ((msg = input.readLine()) != null) {
                display.show(msg);
              } else if (input.read() == -1) {
                break;
              }
            }

            input.close();
            socket.close();
            throw new NoSocketException("No socket is open on this port " + port);
          } catch (IOException | NoSocketException e) {
            e.printStackTrace();
          }
        }
      }).start();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}