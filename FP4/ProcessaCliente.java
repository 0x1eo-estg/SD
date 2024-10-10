/* A classe ProcessaCliente recebe mensagens do seu cliente, adicionando-
lhes a data e hora, bem como o IP do cliente. Devendo de seguida,
armazenar a mensagem centralmente. */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class ProcessaCliente implements Runnable {
    private Socket clientSocket;
    private List<String> messages;

    public ProcessaCliente(Socket clientSocket, List<String> messages) {
        this.clientSocket = clientSocket;
        this.messages = messages;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}