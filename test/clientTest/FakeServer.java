package clientTest;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FakeServer {
    private int port;

    public FakeServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            ServerSocket fakeServer = new ServerSocket(port);
            Socket connection = fakeServer.accept();
            PrintStream printer = new PrintStream(connection.getOutputStream());
            printer.println("Hello");
            fakeServer.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
