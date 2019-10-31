package my;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GridDijkstra {
	
	class Tile{
		int y;
		int x;
		int cost;
		int distance;
		char symbol;
		boolean visited;
		Tile parent;
		
		Tile(int y, int x, char symbol){
			this.y = y;
			this.x = x;
			this.symbol = symbol;
			this.cost = 1;
			this.distance = -1;
			this.visited = false;
			this.parent = null;
		}
	}
	
	class Edge{
		Tile src;
		Tile dst;
		int currDistance;
		
		Edge(Tile src, Tile dst, int currDistance){
			this.src = src;
			this.dst = dst;
			this.currDistance = currDistance;
		}
	}
	
	final String[] maze = {
			"##########",
			"#    #   #",
			"#    #   #",
			"#        #",
			"#    #   #",
			"###  #   #",
			"#    #   #",
			"#        #",
			"#    #   #",
			"##########"
	};
	
	final int[] dirY = {1, 0, -1, 0};
	final int[] dirX = {0, 1, 0, -1};
	final int dirCount = 4;
	final int startIndex = 1;
	final int endIndex = 8;
	boolean found = false;
	
	Tile map[][] = new Tile[10][11];
	Tile srcTile;
	Tile dstTile;
	
	ArrayList<Edge> queue = new ArrayList<>();	
	
	public GridDijkstra() {
		
		initMap();				
		initTiles();
		
		addNeighborFromTile(srcTile);
		
		while(!found && !queue.isEmpty()) {
			
			Edge curr = queue.remove(0);
			
			if(curr.dst.visited) {
				continue;
			}
			
			markEdgeAsVisited(curr);
			
			if(curr.dst.equals(dstTile)) {
				found = true;
				continue;
			}
			
			addNeighborFromTile(curr.dst);
			
			Collections.sort(queue, new Comparator<Edge>() {

				@Override
				public int compare(Edge a, Edge b) {
					return a.currDistance - b.currDistance;
				}
				
			});
		}
		
		backTrack(dstTile);
		
		printMap();
		
	}
	
	private void backTrack(Tile curr) {
		if(!curr.equals(srcTile)) {
			backTrack(curr.parent);
		}
		if(!curr.equals(dstTile) && !curr.equals(srcTile)) {
			curr.symbol = 'o';
		}
		
		printMap();
		System.out.println();
	}

	private void markEdgeAsVisited(Edge curr) {
		curr.dst.visited = true;
		curr.dst.parent = curr.src;
		curr.dst.distance = curr.currDistance;
	}

	private void addNeighborFromTile(Tile curr) {		
		for(int i = 0; i < 4; i++) {
			int nextY = curr.y + dirY[i];
			int nextX = curr.x + dirX[i];
			
			if(nextY < startIndex || nextY > endIndex || nextX < startIndex || nextX > endIndex) {
				continue;
			}
			
			Tile nextTile = map[nextY][nextX];
			
			if(nextTile.visited || nextTile.symbol == '#') {
				continue;
			}
			
			queue.add(new Edge(curr, nextTile, curr.distance + nextTile.cost));
		}
		
	}
	
	private void initTiles() {
		srcTile = map[1][1];
		dstTile = map[8][8];
		srcTile.distance = 0;
		srcTile.symbol = 'S';
		dstTile.symbol = 'F';
	}

	private void initMap() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				map[i][j] = new Tile(i, j, maze[i].charAt(j));
			}
		}
	}

	private void printMap() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print(map[i][j].symbol);
			}
			System.out.println();
		}
	}	
}
