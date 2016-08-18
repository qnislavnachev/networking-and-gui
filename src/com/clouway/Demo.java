package com.clouway;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Demo {
  public static void main(String[] args) {
    ConnectedClients connectedClients = new ConnectedClients();

    Display display = message -> {
      System.out.println(message);
      return message;
    };

    Server server = new Server(8080, connectedClients);
    Thread thread = new Thread(server);

    Client client = new Client("", 8080, display);
    Client client1 = new Client("", 8080, display);

    Thread clientThread = new Thread(client);
    Thread clientThread1 = new Thread(client1);

    thread.start();

    clientThread.start();
    clientThread1.start();
  }
}
