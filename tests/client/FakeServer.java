package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class FakeServer extends Thread {
    private int port;
    private ServerSocket fakeServer;
    private String message;
    private BlockingQueue<String> queue;

    public FakeServer(int port, BlockingQueue<String> queue) {
        this.port = port;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            fakeServer = new ServerSocket(port);
            while (true) {
                Socket socket = fakeServer.accept();
                PrintStream printer = new PrintStream(socket.getOutputStream());
                printer.println("Server message");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                message = reader.readLine();
                System.out.println(message);
                queue.put("element");
            }
        } catch (SocketException ex) {
            System.out.println("Server fall down");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void shutDown() {
        try {
            fakeServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}