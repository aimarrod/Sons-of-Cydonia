package com.soc.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;
import com.soc.utils.Constants.World;
import com.soc.utils.Tile;

	public class AStar {
		 
		public static AStar instance;
		public Tile [][] tiles;
		
		private AStar(Tile[][] tiles){
			this.tiles=tiles;
		}
		
		public static void initialize(Tile[][] tiles){
			instance = new AStar(tiles);
		}
		
		public Node calculateAStar(Vector2 start, Vector2 goal){
			
			//Graph
			HashMap<String, Node> expanded = new HashMap<String, Node>();
			//Frontier
			PriorityQueue<Node>frontier=new PriorityQueue<Node>();
			
			int goalx = (int) (goal.x*World.TILE_FACTOR);
			int goaly = (int) (goal.y*World.TILE_FACTOR);
			
			//inicio, sin padre, g=0, f
			Node node=new Node((int) (start.x*World.TILE_FACTOR), (int) (start.y*World.TILE_FACTOR), null, 0, calculateHeuristicValue((int) (start.x*World.TILE_FACTOR), (int) (start.y*World.TILE_FACTOR), goalx, goaly));
			frontier.add(node);
			
			double tentative_g_score=0;
			
			while(!frontier.isEmpty()){
				node=frontier.poll();
				
				if((Math.abs(node.x-goalx)<1) && (Math.abs(node.y-goaly)<1d)){
					System.out.println("END");
					return node;
				}
				
				expanded.put(node.id(),node);
				
				List<Node>neighbors=expandNode(node, goalx, goaly);
				for(int i=0;i<neighbors.size();i++){
					
					Node neighbor=neighbors.get(i);
					tentative_g_score=neighbor.g;
					
					if(expanded.containsKey(neighbor.id())&& tentative_g_score >= expanded.get(neighbor.id()).g){
						continue;
					}
					
					if(!frontier.contains(neighbor) || (expanded.containsKey(neighbor.id()) && tentative_g_score < expanded.get(neighbor.id()).g)){
						if(!frontier.contains(neighbor)){
							frontier.add(neighbor);
						} else {
							neighbor = expanded.get(neighbor.id());
							neighbor.parent = node;
						}
						neighbor.g = tentative_g_score;					
					}
				}
			}
			return null;	
		}
		public double calculateHeuristicValue(int startx, int starty, int goalx, int goaly){
			return 	Math.hypot(goalx-startx, goaly-starty); //Como SQRT pero mas rapido
		}
		
		public boolean isGoal(int currx, int curry, int goalx, int goaly){
			return (Math.abs(currx-goalx)<1) && (Math.abs(curry-goaly)<1);
		}
		
		public List<Node> expandNode(Node n,int goalx, int goaly){
			List<Node> nodes = new ArrayList<Node>();
			addSafe(n, n.x+1, n.y+1, goalx, goaly, nodes, 2);
			addSafe(n, n.x+1, n.y, goalx, goaly, nodes, 1);
			addSafe(n, n.x+1, n.y-1, goalx, goaly, nodes, 2);
			addSafe(n, n.x, n.y-1, goalx, goaly, nodes, 1);
			addSafe(n, n.x-1, n.y-1, goalx, goaly, nodes, 2);
			addSafe(n, n.x-1, n.y, goalx, goaly, nodes, 1);
			addSafe(n, n.x-1, n.y+1, goalx, goaly, nodes, 2);
			addSafe(n, n.x, n.y+1, goalx, goaly, nodes, 1);

			return nodes;
		}
		
		public void addSafe(Node parent, int x, int y, int goalx, int goaly, List<Node> nodes, int cost){
			if((x>0 && x<tiles.length && y>0 && y<tiles[x].length && tiles[x][y].type==0)){
				nodes.add(new Node(x,y, parent, parent.g+cost, Math.hypot(goalx-x, goaly-y)));
			}
		}
		
		public ArrayList<Node> getPath (Vector2 start, Vector2 goal){
			 ArrayList<Node>pathNodes=new ArrayList<Node>();
			 long time = System.currentTimeMillis();
			 Node path=calculateAStar(start, goal);
			 System.out.println("Time: " + (System.currentTimeMillis() - time));
			 Node currentNode=path;
			 while(currentNode!=null){
				  currentNode.x = (int) (currentNode.x*World.TILE_SIZE)+16;
				  currentNode.y = (int) (currentNode.y*World.TILE_SIZE)+16;
				  pathNodes.add(0,currentNode);
				  currentNode=currentNode.parent;
			 }
			 return pathNodes;
		}
		
		public boolean isDirectPath(int posx, int posy, int goalx, int goaly){
			posx = (int) (posx*World.TILE_FACTOR);
			posy = (int) (posy*World.TILE_FACTOR);
			goalx = (int) (goalx*World.TILE_FACTOR); 
			goaly = (int) (goaly*World.TILE_FACTOR);
			
			while(posx != goalx || posy != goaly){
				if(posx > goalx){
					posx--;
				} else if(posx < goalx){
					posx++;
				}
				if(tiles[posx][posy].type!=0){
					return false;
				}
				
				if(posy > goaly){
					posy--;
				} else if(posy < goaly){
					posy++;
				}
				if(tiles[posx][posy].type!=0){
					return false;
				}
			}
			return true;
		}
	}
