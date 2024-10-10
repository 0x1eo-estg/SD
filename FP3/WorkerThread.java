import java.io.*;
import java.net.*;

public class WorkerThread extends Thread {
    private Socket socket = null;

    public WorkerThread (Socket socket) {
        super("WorkerThread");
        this.socket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("Bye")) {
                    break;
                }
                outputLine = "Server: " + inputLine;
                out.println(outputLine);
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}