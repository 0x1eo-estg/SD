import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 7.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String serverIP = serverSocket.getInetAddress().getHostAddress();
        String clientIP = clientSocket.getInetAddress().getHostAddress();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            String response = serverIP + ":" + clientIP + ": " + inputLine;
            out.println(response);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}