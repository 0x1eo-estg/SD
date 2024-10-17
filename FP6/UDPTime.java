import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.io.IOException;

public class UDPTime {
    public static void main(String[] args) {
        // Porta de comunicação
        int port = 4445;
        try {
            // Criar o socket UDP
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Servidor de hora iniciado na porta " + port);
            
            // Enviar mensagens em loop
            while (true) {
                // Obter a hora atual
                String message = new Date().toString();
                byte[] buffer = message.getBytes();
                
                // Receber datagrama do cliente
                DatagramPacket packet = new DatagramPacket(new byte[256], 256);
                socket.receive(packet);
                
                // Criar datagrama e enviar para o cliente
                packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
                socket.send(packet);
                System.out.println("Hora enviada: " + message);
                
                // Pausa para simular o envio periódico
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
