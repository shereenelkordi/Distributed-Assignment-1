import java.rmi.Remote;

public interface Graph extends Remote {

    public void instantiate() throws Exception;
    public void submitBatch(String filepath) throws Exception;

}
