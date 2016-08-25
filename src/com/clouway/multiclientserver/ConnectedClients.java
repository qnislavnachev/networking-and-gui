package com.clouway.multiclientserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ConnectedClients {
  private ArrayList<Socket> clients = new ArrayList<>();

  public synchronized void add(Socket socket) {
    clients.add(socket);
  }

  public void notifyAllClients() {
    for (Socket clientSocket : clients) {
      try {
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        output.println("Client " + count() + " connected to the server.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public Integer count() {
    return clients.size();
  }
}
