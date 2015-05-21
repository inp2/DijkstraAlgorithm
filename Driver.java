import java.util.*;
import java.io.*;

public class Driver
{
	public static int menuNumber = 0;
	public static Graph G;
	public static In in;
	public static Scanner keyboard;
	public static EdgeWeightedGraph E;
	public static EdgeWeightedDigraph DE;
	public static Digraph D;
	public static DijkstraSP sp;
	public static int i;
	public static int j;
	public static double x;
	public static double totalWeight;
	public static HashMap<Integer, Iterable<Edge>> deletedNodes = new HashMap<Integer, Iterable<Edge>>();
	public static boolean [] neighbors;
	public static int DFScounter = 0;

	public static void main(String [] args)
	{
		if(args.length < 0)
		{
			System.out.println("Please enter the proper commandline arguments");
		}
		else
		{
     	   in = new In(args[0]);
     	   E = new EdgeWeightedGraph(in);
     	   keyboard = new Scanner(System.in);
     	   menu();
	   	}
	}
	public static void menu()
	{
		System.out.println(" ");
		System.out.println("               MENU                 ");
		System.out.println("------------------------------------");
		System.out.println("1. Report");
		System.out.println("2. Minimum Spanning Tree");
		System.out.println("3. Display Shortest Path");
		System.out.println("4. Display Each Of The Distinct Paths");
		System.out.println("5. Down");
		System.out.println("6. Up");
		System.out.println("7. Change weight");
		System.out.println("8. Quit");
		System.out.print("Please Enter Number Of Your Choice: ");
		menuNumber = keyboard.nextInt();
		switch(menuNumber)
		{
			//Report
			case 1:
				System.out.println(" ");
				System.out.println("REPORT");
				System.out.println("-------");
				//Print out the graph
				StdOut.println(E);
				System.out.println("Down Nodes");
				for(Map.Entry<Integer, Iterable<Edge>> entry : deletedNodes.entrySet())
				{
					int key = entry.getKey();
					Iterable<Edge> edge = entry.getValue();
					System.out.println("Node: " + key);
				}
				//Return to the menu
				menu();
				break;
			//Minimum Spanning Tree
			case 2:
				System.out.println(" ");
				System.out.println("Minimum Spanning Tree");
				System.out.println("---------------------");
				//Compute the MST
				PrimMSTTrace mst = new PrimMSTTrace(E);
				//Print out the total weight of the MST
				StdOut.println("total weight = " + mst.weight());
				//Print out all the Edges in the minimum spanning tree
				for (Edge e : mst.edges())
				{
					StdOut.println(e);
				}
				//Return to the menu
				menu();
				break;
			case 3:
				System.out.println(" ");
				System.out.print("Please enter vertex i: ");
				i = keyboard.nextInt();
				System.out.print("Please enter vertex j: ");
				j = keyboard.nextInt();
				System.out.println(" ");
				System.out.println("Shortest Path");
				System.out.println("-------------");
				MakeEdgeWeightedDigraph(E);
				//POSSIBLY CHANGE DIJKSTRASP
				sp = new DijkstraSP(DE, i);
				//Print the shortest path
				for (int t = i; t <= j; t++)
				{
					if (sp.hasPathTo(j))
					{
						for(DirectedEdge e : sp.pathTo(j))
						{
							StdOut.print(e + "   ");
                    	}
						break;
				    }
				    else
				    {
				       	StdOut.printf("%d to %d no path\n", i, j);
				    }
        		}
				menu();
				break;
			case 4:
				System.out.println(" ");
				System.out.print("Please enter vertex i: ");
				i = keyboard.nextInt();
				System.out.print("Please enter vertex j: ");
				j = keyboard.nextInt();
				System.out.print("Please enter total weight: ");
				x = keyboard.nextDouble();
				StringBuilder string = new StringBuilder("Path");
				neighbors = new boolean [E.V()];
				//DepthFirstSearch
				DepthFirstSearch(i, j, 0, string, x);
				System.out.println("The total number of possible paths are: " + DFScounter);
				menu();
				break;
			case 5:
				System.out.println(" ");
				System.out.println("Please enter node i: ");
				i = keyboard.nextInt();
				if(deletedNodes.containsKey(i))
				{
					System.out.println("This node is already removed from the graph");
				}
				else
				{
					deletedNodes.put(i, E.adj(i));
					System.out.println("Node has been removed");
				}
				menu();
				break;
			case 6:
				System.out.println(" ");
				System.out.println("Please enter node i: ");
				i = keyboard.nextInt();
				//Check if it was a removed edge
				if(deletedNodes.containsKey(i))
				{
					deletedNodes.remove(i);
					System.out.println("This edge has been added");
				}
				else
				{
					System.out.println("This edge cannot be added");
				}
				menu();
				break;
			case 7:
				System.out.println(" ");
				System.out.println("Please enter vertex i: ");
				i = keyboard.nextInt();
				System.out.println("Please enter vertex j: ");
				j = keyboard.nextInt();
				System.out.println("Please enter edge weight: ");
				x = keyboard.nextInt();
				if(x <= 0)
				{
					if(deletedNodes.containsKey(i))
					{
						for(Edge e : E.edges())
						{
							if(i == e.either() && j == e.other(i))
							{
								e.updateWeight(x);
								deletedNodes.put(i, E.adj(i));
								System.out.println("This edge has been updated.");
							}
						}
					}
					else
					{
						for(Edge e : E.edges())
						{
							if(i == e.either() && j == e.other(i))
							{
								e.updateWeight(x);
								deletedNodes.put(i, E.adj(i));
								System.out.println("This node has been removed.");
								System.out.println("This edge has been updated.");
							}
						}
					}
				}
				else
				{
					for(Edge e : E.edges())
					{
						if(i == e.either() && j == e.other(i))
						{
							e.updateWeight(x);
						}
					}
				}
				menu();
				break;
			case 8:
				System.out.println(" ");
				System.out.println("Thank-you, now closing");
				System.exit(0);
				break;
			}
		}

	public static void MakeEdgeWeightedDigraph(EdgeWeightedGraph G)
	{
		DE = new EdgeWeightedDigraph(E.V());
		for(Edge e : E.edges())
		{
			if(deletedNodes.containsValue(e))
			{
			}
			else
			{
				int vertex1 = e.either();
				int vertex2 = e.other(vertex1);
				double weight1 = e.weight();
				DirectedEdge de = new DirectedEdge(vertex1, vertex2, weight1);
				DE.addEdge(de);
				DirectedEdge de2 = new DirectedEdge(vertex2, vertex1, weight1);
				DE.addEdge(de2);
			}
		}
	}

	public static void DepthFirstSearch(int currentLocation, int endingVertex, double currentDist, StringBuilder currentPath, double weightLimit)
	{
		StringBuilder newPath;
		String stupidStringVariable;

	   	if(neighbors[currentLocation] == true)
	    {
			return;
		}
		if(currentDist > weightLimit)
	    {
	        return;
	    }
	    if(endingVertex == currentLocation)
	    {
	        stupidStringVariable = currentPath.toString();
	        newPath = new StringBuilder();
	        newPath.append(stupidStringVariable);
	        newPath.append(" - " + endingVertex);
	        System.out.println(newPath);
	        DFScounter++;
	        return;
	    }
	    neighbors[currentLocation] = true;

	   	for(Edge e : E.adj(currentLocation))
	    {
			if(deletedNodes.containsValue(e))
			{
			}
			else
			{
	     	   stupidStringVariable = currentPath.toString();
	     	   newPath = new StringBuilder();
	     	   newPath.append(stupidStringVariable);
	     	   newPath.append(" - " + currentLocation);
	     	   DepthFirstSearch(e.other(currentLocation), endingVertex, currentDist + e.weight(), newPath, weightLimit);
		   	}
	     }
	     neighbors[currentLocation] = false;
	    }
	 }
