package com.clouway;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Server implements Runnable {
  private Integer port;
  private ConnectedUsers connectedUsers;
  private ServerSocket serverSocket;

  public Server(Integer port, ConnectedUsers connectedUsers) {
    this.port = port;
    this.connectedUsers = connectedUsers;
  }

  @Override
  public void run() {
    try {
      serverSocket = new ServerSocket(port);
      while (true) {//todo can use flag(bolean)
        Socket clientSocket = serverSocket.accept();
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        Integer userCount = connectedUsers.getUserCount() +1 ;
        output.println("Welcome, you are user number " + userCount);
        connectedUsers.add(clientSocket);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        serverSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void shutdownServer() {
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
