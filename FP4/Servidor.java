import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PORT = 2048;
    private static final int THREAD_POOL_SIZE = 10;
    private static final int BROADCAST_INTERVAL = 5000; // 5 seconds

    private static final List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
    private static final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Echo server started on port " + PORT);

            // Thread to broadcast messages to all clients periodically
            scheduler.scheduleAtFixedRate(() -> {
                synchronized (clients) {
                    Iterator<Socket> iterator = clients.iterator();
                    while (!iterator.hasNext()) {
                        Socket client = iterator.next();
                        try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                            synchronized (messages) {
                                for (String message : messages) {
                                    out.println(message);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            iterator.remove();  // Remove the client from the list
                        }
                    }
                    messages.clear();
                }
            }, 0, BROADCAST_INTERVAL, TimeUnit.MILLISECONDS);
            

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            scheduler.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("Bye")) {
                        break;
                    } else {
                        System.out.println("Received: " + inputLine);
                        synchronized (messages) {
                            messages.add(inputLine);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    clients.remove(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}