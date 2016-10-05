package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class FakeServer extends Thread {
    private int port;
    private ServerSocket fakeServer;

    public FakeServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            fakeServer = new ServerSocket(port);
            while (true) {
                Socket socket = fakeServer.accept();
                PrintStream printer = new PrintStream(socket.getOutputStream());
                printer.println("Server message");
            }
        } catch (SocketException ex) {
            System.out.println("Server fall down");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        try {
            fakeServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}