import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        Socket echSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echSocket = new Socket("localhost", 7);
            out = new PrintWriter(echSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know host EchoSlient");
        } catch (IOException e) {
            System.err.println("Couldn't get IO for the connection to EchoSlient");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println(in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echSocket.close();
    }
}