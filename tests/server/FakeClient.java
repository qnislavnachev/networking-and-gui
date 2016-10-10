package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class FakeClient extends Thread {
    private String message;
    private String host;
    private int port;
    private BlockingQueue<String> queue;
    private InputStream inputStream;

    public FakeClient(String host, int port, BlockingQueue<String> queue, InputStream inputStream) {
        this.host = host;
        this.port = port;
        this.inputStream = inputStream;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (Socket fakeClient = new Socket(host, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fakeClient.getInputStream()));
            message = reader.readLine();
            System.out.println(message);
            serverNotify(fakeClient);
        } catch (SocketException ex) {
            System.out.println("Server fall down !");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String getMessage() {
        return message;
    }

    public void serverNotify(Socket socket) throws IOException, InterruptedException {
        PrintStream printer = new PrintStream(socket.getOutputStream());
        Scanner scanner = new Scanner(inputStream);
        queue.put("element");
        while (scanner.hasNext()) {
            printer.println(scanner.nextLine());
        }
    }
}
