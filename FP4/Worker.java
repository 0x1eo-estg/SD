import java.util.ArrayList;
import java.util.List;

public class Worker extends Thread {
    ArrayList<String> frases;
    int numero;

    public Worker(ArrayList<String> f, int n) {
        super("Worker");
        this.frases = f;
        this.numero = n;
    }

    public Worker(List<Object> asFrases, int i) {
        //TODO Auto-generated constructor stub
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                synchronized (frases) {
                    frases.add("Frase " + i + " da thread " + numero);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}