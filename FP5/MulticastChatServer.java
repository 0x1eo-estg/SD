import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastChatServer {

    public static void main(String[] args) {
        String multicastAddress = "230.0.0.0"; // Endereço IP para multicast
        int port = 4446; // Porta de comunicação
        MulticastSocket socket = null;
        try {
            // Criar o socket multicast
            socket = new MulticastSocket();
            InetAddress group = InetAddress.getByName(multicastAddress);
            
            // Enviar mensagens em loop
            while (true) {
                String message = "Mensagem do servidor para o grupo";
                byte[] buffer = message.getBytes();
                
                // Criar datagrama e enviar para o grupo multicast
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);
                System.out.println("Mensagem enviada: " + message);
                
                // Pausa para simular o envio periódico
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
