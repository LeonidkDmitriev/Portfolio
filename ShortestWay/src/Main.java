public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(10);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(6, 8);
        g.addEdge(5, 7);
      //  g.addEdge(8, 9);
       g.addEdge(7, 9);
        g.addEdge(1, 6);
        g.addEdge(3, 8);
        g.addEdge(1, 2);
        g.addEdge(7, 4);

        BreadthFirstPaths bfsp = new BreadthFirstPaths(g, 0);

        System.out.println(bfsp.hasPathTo(9)); // есть ли путь до указанной вершины
        System.out.println(bfsp.pathTo(9));// кратчайший путь до указанной вершины
        System.out.println(bfsp.distTo(9));// расстояние до указанной вершины
    }
}
