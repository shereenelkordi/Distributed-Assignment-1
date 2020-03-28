import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.*;

class Pair{
    int src;
    int dest;
    Pair(int src, int dest){
        this.src = src;
        this.dest = dest;
    }
}
public class GraphC extends UnicastRemoteObject implements Graph {

    public GraphC() throws RemoteException {
        super();
    }
    //for creating output files per batch
    int log = 0;
    List<Pair> graph = new ArrayList<>();
    @Override
    public void instantiate() throws Exception {
        File file = new File("initialGraph.txt");
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = bf.readLine();
        while(line.charAt(0) != 'S'){
            int src = Integer.parseInt(String.valueOf(line.charAt(0)));
            int dest = Integer.parseInt(String.valueOf(line.charAt(2)));
            Pair edge = new Pair(src,dest);
            graph.add(edge);
            line = bf.readLine();
        }
        bf.close();
        System.out.println("R");
    }

    @Override
    public void submitBatch(String filepath) throws Exception {
        BufferedReader bf = new BufferedReader(new FileReader(filepath));
        String line = bf.readLine();;
        PrintWriter writer = new PrintWriter("log"+log+".txt ", "UTF-8");

        while(line.charAt(0) != 'F'){
            char op = line.charAt(0);
            int src = Integer.parseInt(String.valueOf(line.charAt(2)));
            int dest = Integer.parseInt(String.valueOf(line.charAt(4)));
            if(op == 'A'){
                add(src,dest);
            }
            else if(op == 'D'){
                delete(src,dest);
            }
            else{
                int result = query(src,dest);
                writer.println(result);
            }
            line = bf.readLine();
        }
        bf.close();
        writer.close();
        log++;
    }

    private void add(int src, int dest){
        if(findEdge(src,dest) == -1){
            Pair newEdge = new Pair(src,dest);
            graph.add(newEdge);
        }
    }
    private void delete(int src, int dest){
        int index = findEdge(src,dest);
        if(index != -1){
            graph.remove(index);
        }
    }
    private int query(int src, int dest){
        if(src == dest){
            return 0;
        }
        return BFS(src, dest);
    }

    private int BFS(int src, int dest){
        Queue<Integer> queue = new LinkedList();
        List<Integer> visited = new ArrayList<>();
        Map<Integer,Integer> distance = new HashMap<>();

        queue.add(src);
        distance.put(src,0);
        visited.add(src);

        while(!queue.isEmpty()){
            int vertex = queue.poll();
            ArrayList<Integer> adj = getAdjacent(vertex);
            for (int current : adj) {
                if (!visited.contains(current)) {
                    visited.add(current);
                    int currDistance = distance.get(vertex) + 1;
                    distance.put(current, currDistance);
                    queue.add(current);

                    if (current == dest) {
                        return currDistance;
                    }
                }
            }

        }
        return -1;
    }

    private ArrayList<Integer> getAdjacent(int src){
        ArrayList<Integer> adj = new ArrayList<>();
        for (Pair graphEdge : graph) {
            if (graphEdge.src == src) {
                adj.add(graphEdge.dest);
            }
        }
        return adj;
    }
    private int findEdge(int src, int dest){
        for(int i = 0; i<graph.size();i++){
            Pair graphEdge = graph.get(i);
            if(graphEdge.src == src && graphEdge.dest == dest){
                return i;
            }
        }
        return -1;
    }

}

