import java.rmi.*;

public class Client {

    public static void main(String[] args) throws Exception {
        Graph cobj = (Graph) Naming.lookup("graph");

        cobj.instantiate();
        cobj.submitBatch("batch1.txt");
        cobj.submitBatch("batch2.txt");
    }
}
