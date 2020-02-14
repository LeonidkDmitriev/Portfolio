import java.util.LinkedList;

public class DeppthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int source;

    public DeppthFirstPaths(Graph g, int source) {
        if (source < 0) {
            throw new IllegalArgumentException("У вершины не может быть отрицательный номер");
        }
        this.source = source;
        edgeTo = new int[g.vertexCount()];
        marked = new boolean[g.vertexCount()];
        dfs(g, source);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w:
                g.adjList(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int dist) {return marked[dist];}

    public LinkedList<Integer> pathTo(int dist) {
        if (!hasPathTo(dist)) {
            return null;
        }

        LinkedList<Integer> stack = new LinkedList<>();

        int vertex = dist;
        while (vertex != source) {
            stack.push(vertex);
            vertex = edgeTo[vertex];
        }
        return stack;
    }
}
