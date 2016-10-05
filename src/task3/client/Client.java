package task3.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    private String message;

    public void join(String host, int port) {
        try (Socket client = new Socket(host, port)) {
            messageListener(client).start();
            PrintStream printer = new PrintStream(client.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            while (true) {
                printer.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    private Thread messageListener(Socket socket) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (SocketException ex) {
                    System.out.println("Server fall down !");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }
}
