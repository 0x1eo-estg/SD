import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class MulticastChatClient {

    public static void main(String[] args) {
        String multicastAddress = "230.0.0.0"; // O mesmo endereço usado pelo servidor
        int port = 4446; // A mesma porta usada pelo servidor
        MulticastSocket socket = null;

        try {
            // Criar o socket multicast e ingressar no grupo
            socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(multicastAddress);
            socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

            System.out.println("Cliente conectado ao grupo multicast.");

            // Loop para receber mensagens
            byte[] buffer = new byte[256];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Mensagem recebida: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.leaveGroup(new InetSocketAddress(InetAddress.getByName(multicastAddress), port), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
