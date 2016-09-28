package serverTest;

import task2.server.Server;

public class RunServer {
    public static void main(String[] args) {
        Server server = new Server(1111);
        server.start();
    }
}
