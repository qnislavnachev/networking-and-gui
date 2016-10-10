package task3.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Client extends Thread {
    private String message;
    private String host;
    private int port;
    private InputStream inputStream;
    private BlockingQueue<String> queue;

    public Client(InputStream inputStream, BlockingQueue<String> queue) {
        this.inputStream = inputStream;
        this.queue = queue;
    }

    public void run() {
        try (Socket client = new Socket(host, port)) {
            messageListener(client).start();
            PrintStream printer = new PrintStream(client.getOutputStream());
            Scanner scanner = new Scanner(inputStream);
            queue.put("element");
            while (scanner.hasNext()) {
                printer.println(scanner.nextLine());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void join(String host, int port) {
        this.host = host;
        this.port = port;
        start();
    }

    private Thread messageListener(Socket socket) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (SocketException ex) {
                    System.out.println("Server fall down !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        return thread;
    }
}