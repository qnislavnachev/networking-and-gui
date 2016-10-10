package task3.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class ClientNotifier extends Thread {
    private List<Socket> list;

    public ClientNotifier(List<Socket> list) {
        this.list = list;
    }

    @Override
    public void run() {
        try {
            for (Socket each : list) {
                PrintStream printer = new PrintStream(each.getOutputStream());
                printer.println("New client detected: " + list.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
