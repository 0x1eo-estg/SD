import java.util.ArrayList;

public class ObjSharing extends Worker {
    public ObjSharing(ArrayList<String> f, int n) {
        super(f, n);
        //TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        int NThreads = 5;
        ArrayList<String> asFrases = new ArrayList<>();

        for (int i = 0; i < NThreads; i++)
            new Worker(asFrases, i).start();

        for (int j = 0; j < 6; j++) {
            try {
                Thread.sleep(100); // Adding sleep to simulate some delay
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            synchronized (asFrases) {
                for (int k = 0; k < asFrases.size(); k++)
                    System.out.println(asFrases.get(k));
            }
        }
    }
}