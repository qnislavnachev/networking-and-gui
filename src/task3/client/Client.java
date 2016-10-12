package task3.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client extends Thread {
    private String message;
    private String host;
    private int port;
    private InputStream inputStream;

    public Client(String host, int port, InputStream inputStream) {
        this.host = host;
        this.port = port;
        this.inputStream = inputStream;
    }

    public void run() {
        try (Socket client = new Socket(host, port)) {
            messageSender(client).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    private Thread messageSender(Socket socket) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    PrintStream printer = new PrintStream(socket.getOutputStream());
                    Scanner scanner = new Scanner(inputStream);
                    while (scanner.hasNext()) {
                        printer.println(scanner.nextLine());
                    }

                } catch (SocketException ex) {
                    System.out.println("Server fall down !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}