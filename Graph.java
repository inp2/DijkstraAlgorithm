/*************************************************************************
 *  Compilation:  javac Graph.java
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *************************************************************************/

/**
 *  The Graph class represents an undirected graph of vertices
 *  named 0 through V-1. It supports the following operations: add an edge to the graph,
 *  iterate over all of the neighbors adjacent to a vertex. Parallel edges and self-loops are permitted.
 */

public class Graph
{
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

   /**
     * Create an empty graph with V vertices.
     * @throws java.lang.IllegalArgumentException if V < 0
     */
    public Graph(int V)
    {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
        {
            adj[v] = new Bag<Integer>();
        }
    }

   /**
     * Create a random graph with V vertices and E edges.
     * Expected running time is proportional to V + E.
     * @throws java.lang.IllegalArgumentException if either V < 0 or E < 0
     */
    public Graph(int V, int E)
    {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            addEdge(v, w);
        }
    }

   /**
     * Create a digraph from input stream.
     */
    public Graph(In in)
    {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++)
        {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

   /**
     * Copy constructor.
     */
    public Graph(Graph G)
    {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++)
        {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v])
            {
                reverse.push(w);
            }
            for (int w : reverse)
            {
                adj[v].add(w);
            }
        }
    }

   /**
     * Return the number of vertices in the graph.
     */
    public int V()
    {
		return V;
	}

   /**
     * Return the number of edges in the graph.
     */
    public int E() { return E; }


   /**
     * Add the undirected edge v-w to graph.
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


   /**
     * Return the list of neighbors of vertex v as in Iterable.
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v];
    }


   /**
     * Return a string representation of the graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}