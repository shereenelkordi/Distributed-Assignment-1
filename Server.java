import java.rmi.*;

public class Server {
    public static void main(String[] args)throws Exception {
        GraphC obj = new GraphC();
        Naming.rebind("graph",obj);
        System.out.println("Server Started");
    }
}
