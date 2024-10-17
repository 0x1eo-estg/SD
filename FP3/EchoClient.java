import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("localhost", 2048);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader inReader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            in = inReader;
        } catch (UnknownHostException e) {
            System.err.println("Don't know host EchoServer");
        } catch (IOException e) {
            System.err.println("Couldn't get IO for the connection to EchoServer");
            System.exit(1);
        }


        final BufferedReader finalIn = in;
        final PrintWriter finalOut = out;
        // Thread for receiving and printing messages from the server
        Thread receiveThread = new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = finalIn.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);
                }
            } catch (IOException e) {
                System.err.println("Error reading from server.");
            }
        });

        // Thread for getting user input and sending messages to the server
        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    finalOut.println(userInput);
                }
                stdIn.close();
            } catch (IOException e) {
                System.err.println("Error reading user input.");
            }
        });

        receiveThread.start();
        sendThread.start();

        try {
            receiveThread.join();
            sendThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted.");
        }

        out.close();
        in.close();
        echoSocket.close();
    }
}