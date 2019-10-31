package my;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatrixDijkstra {
	class Node{
		int no;
		int distance;
		boolean visited;
		Node parent;

		Node(){
			distance = infinity;
			visited = false;
			parent = null;
		}

		Node(int no){
			this.no = no;
			distance = infinity;
			visited = false;
			parent = null;
		}
	}

	class Edge{
		Node src;
		Node dst;
		int currDistance;

		Edge(Node src, Node dst, int currDistance){
			this.src = src;
			this.dst = dst;
			this.currDistance = currDistance;
		}
	}


	final int adjacencyMatrix[][] = { 
			{ 0, 4, 0, 0, 0, 0, 0, 8, 0 }, 
			{ 4, 0, 8, 0, 0, 0, 0, 11, 0 }, 
			{ 0, 8, 0, 7, 0, 4, 0, 0, 2 }, 
			{ 0, 0, 7, 0, 9, 14, 0, 0, 0 }, 
			{ 0, 0, 0, 9, 0, 10, 0, 0, 0 }, 
			{ 0, 0, 4, 14, 10, 0, 2, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 2, 0, 1, 6 }, 
			{ 8, 11, 0, 0, 0, 0, 1, 0, 7 }, 
			{ 0, 0, 2, 0, 0, 0, 6, 7, 0 } }; 

	final int num_node = 9;
	final int infinity = 999999;
	boolean found = false;

	Node srcNode;
	Node dstNode;
	
	Node graph[] = new Node[num_node];
	ArrayList<Edge> queue = new ArrayList<>();

	MatrixDijkstra(){
		initGraph();

		while(!found && !queue.isEmpty()) {

			Edge curr = queue.remove(0);

			if(curr.dst.visited) {
				continue;
			}

			markEdgeAsVisited(curr);
			
			if(curr.dst.equals(dstNode)) {
				found = true;
				break;
			}

			// You may want to use a more preferable data structures
			// such as Heap to avoid sorting the vertices in every
			// iteration.
			sortEdgesAscending();
		}

		printResultsAndTotalCost();		
		printBackTrack();
	}

	private void sortEdgesAscending() {
		Collections.sort(queue, new Comparator<Edge>() {

			@Override
			public int compare(Edge a, Edge b) {
				return a.currDistance - b.currDistance;
			}

		});
	}

	private void printBackTrack() {
		System.out.println("Start Backtrack!");
		backTrack(dstNode);
		System.out.println("Done!");
	}

	private void printResultsAndTotalCost() {
		System.out.println("Done!");
		System.out.println("Total cost from " + srcNode.no + " to " + dstNode.no + " : " + dstNode.distance);
	}

	private void markEdgeAsVisited(Edge curr) {
		curr.dst.visited = true;
		curr.dst.parent = curr.src;
		curr.dst.distance = curr.currDistance;
		addNeighborFromNode(curr.dst);
	}

	private void initGraph() {
		for(int i = 0; i < num_node; i++) {
			graph[i] = new Node(i);
		}

		srcNode = graph[0];
		dstNode = graph[4];

		srcNode.distance = 0;
		addNeighborFromNode(srcNode);
	}

	void addNeighborFromNode(Node curr) {
		for(int next = 0; next < num_node; next++) {
			int nextCost = adjacencyMatrix[curr.no][next];

			if( nextCost == 0 || graph[next].visited == true) {
				continue;
			}			

			queue.add(new Edge(graph[curr.no], graph[next], curr.distance + nextCost));
		}
	}

	void backTrack(Node curr) {
		if(!curr.equals(srcNode)) {
			backTrack(curr.parent);
		}

		System.out.print(curr.no + " >> ");
	}
}
