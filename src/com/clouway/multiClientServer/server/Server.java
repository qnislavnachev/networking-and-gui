package com.clouway.multiClientServer.server;

import com.clouway.multiClientServer.Display;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
public class Server {
  private final Integer port;
  private Display display;
  private ClientList clientList = new ClientList();
  private boolean running = true;

  public Server(Integer port, Display display) {
    this.port = port;
    this.display = display;
  }

  public void openConnection() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
          while (running) {
            try {
              Socket connection = server.accept();
              clientList.add(connection);
              String msg = ("Client " + clientList.size() + " connected.");
              PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
              clientList.notifyClients();
              display.show(msg);
            } catch (IOException ex) {
            }
          }
        } catch (IOException ex) {
          System.err.println("Couldn't start server");
        }
      }
    }).start();
  }

  public void shutDown() {
    running = false;
  }

}