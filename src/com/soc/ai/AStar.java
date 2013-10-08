package com.soc.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.Constants.World;
import com.soc.game.components.Position;
import com.soc.game.map.Tile;

	public class AStar {
		 
		public static AStar instance;
		public Tile [][][] tiles;
		
		private AStar(Tile[][][] tiles){
			this.tiles=tiles;
		}
		
		public static void initialize(Tile[][][] tiles){
			instance = new AStar(tiles);
		}
		
		public Node calculateAStar(Position start, Position goal){
			
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
					return node;
				}
				
				expanded.put(node.id(),node);
				
				List<Node>neighbors=expandNode(node, goalx, goaly, start.z);
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
		
		public List<Node> expandNode(Node n,int goalx, int goaly, int z){
			List<Node> nodes = new ArrayList<Node>();
			addSafe(n, n.x+1, n.y+1, z, goalx, goaly, nodes, 2);
			addSafe(n, n.x+1, n.y, z, goalx, goaly, nodes, 1);
			addSafe(n, n.x+1, n.y-1, z, goalx, goaly, nodes, 2);
			addSafe(n, n.x, n.y-1, z, goalx, goaly, nodes, 1);
			addSafe(n, n.x-1, n.y-1, z, goalx, goaly, nodes, 2);
			addSafe(n, n.x-1, n.y, z, goalx, goaly, nodes, 1);
			addSafe(n, n.x-1, n.y+1, z, goalx, goaly, nodes, 2);
			addSafe(n, n.x, n.y+1, z, goalx, goaly, nodes, 1);

			return nodes;
		}
		
		public void addSafe(Node parent, int x, int y, int z, int goalx, int goaly, List<Node> nodes, int cost){
			if((x>0 && x<tiles[z].length && y>0 && y<tiles[z][x].length && tiles[z][x][y].type!=Constants.World.TILE_OBSTACLE)){
				nodes.add(new Node(x,y, parent, parent.g+cost, Math.hypot(goalx-x, goaly-y)));
			}
		}
		
		public ArrayList<Node> getPath (Position start, Position goal){
			 ArrayList<Node>pathNodes=new ArrayList<Node>();
			 Node path=calculateAStar(start, goal);
			 Node currentNode=path;
			 while(currentNode!=null){
				  currentNode.x = (int) (currentNode.x*World.TILE_SIZE)+16;
				  currentNode.y = (int) (currentNode.y*World.TILE_SIZE)+16;
				  pathNodes.add(0,currentNode);
				  currentNode=currentNode.parent;
			 }
			 return pathNodes;
		}
		
		public boolean isDirectPath(Position pos, Position goal){
			if(pos.z != goal.z) return false;
			int posx = (int) (pos.x*World.TILE_FACTOR);
			int posy = (int) (pos.y*World.TILE_FACTOR);
			int goalx = (int) (goal.x*World.TILE_FACTOR); 
			int goaly = (int) (goal.y*World.TILE_FACTOR);
			
			while(posx != goalx || posy != goaly){
				if(posx > goalx){
					posx--;
				} else if(posx < goalx){
					posx++;
				}
				if(tiles[pos.z][posx][posy].type==Constants.World.TILE_OBSTACLE){
					return false;
				}
				
				if(posy > goaly){
					posy--;
				} else if(posy < goaly){
					posy++;
				}
				if(tiles[pos.z][posx][posy].type==Constants.World.TILE_OBSTACLE){
					return false;
				}
			}
			return true;
		}
	}
