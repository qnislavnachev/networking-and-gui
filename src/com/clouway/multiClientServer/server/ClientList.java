package com.clouway.multiClientServer.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Milev <martinmariusmilev@gmail.com>
 */
class ClientList {
  private List<Socket> clients = new ArrayList<>();

  public synchronized void add(Socket socket) {
    clients.add(socket);
  }


  public synchronized void notifyClients() {
    for (Socket socket : clients) {
      try {
        String msg = "Client number " + (clients.size()) + " connected.";
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(msg);
        out.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public Integer size(){
    return clients.size();
  }
}

