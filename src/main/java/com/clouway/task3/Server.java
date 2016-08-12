package com.clouway.task3;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Server {

    private ServerSocket server = null;
    private Socket connection = null;
    private List<Socket> clients = null;
    private PrintStream out = null;
    private Screen screen;

    public Server(Screen screen) {
        this.screen = screen;
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port, 100);
        clients = new ArrayList();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        connection = server.accept();
                        clients.add(connection);
                        sendInformation();
                    } catch (IOException e) {
                    }
                }
            }
        }.start();
    }

    public void stop() throws IOException {
        for (int i = 0; i < (clients.size() - 1); i++) {
            clients.get(i).close();
        }
        screen.display("Connection to clients is closed!");
        out.close();
        screen.display("Stream to clients is closed!");
        server.close();
        screen.display("Server is closed!");
    }

    private void sendGreeting(Socket client, String message) throws IOException {
        out = new PrintStream(client.getOutputStream(), true);
        out.println(message);
        out.flush();
    }

    private void sendInformation() {
      for (int i = 0; i < (clients.size() - 1); i++) {
          try {
              sendGreeting(clients.get(i), "There's a new client in the list with №" + (clients.size()) + "!");
          } catch (IOException e) {
          }
      }
      try {
          sendGreeting(clients.get((clients.size() - 1)), "Hello, you're client №" + clients.size() + " in the list!");
          screen.display("Hello, you're client №" + clients.size() + " in the list!");
      } catch (IOException e) {
      }
    }
}
