package clientTest;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FakeServer {
    public static void main(String[] args) throws IOException {
        ServerSocket fakeServer = null;
        fakeServer = new ServerSocket(1111);
        Socket connection = fakeServer.accept();
        PrintStream printer = new PrintStream(connection.getOutputStream());
        printer.println("Hello");
        fakeServer.close();
        connection.close();
    }
}
