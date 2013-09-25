package com.soc.algorithms;
import java.util.ArrayList;
import java.util.Collections;

import com.artemis.utils.Bag;
import com.badlogic.gdx.math.Vector2;
import com.soc.utils.Constants;


public class AStar {
	
	public static Node calculateAStar(Vector2 start, Vector2 goal){
		Node finalNode;
		ArrayList<Node>closedSet=new ArrayList<Node>();
		ArrayList<Node>openSet=new ArrayList<Node>();
		Node startNode=new Node(start,null,0,calculateHeuristicValue(start,goal));
		openSet.add(startNode);
		Node currentNode;
		double tentative_g_score=0;
		System.out.println("Antesd del while de Empty Set");
		while(!openSet.isEmpty()){
			Collections.sort(openSet);
			currentNode=openSet.get(0);
			if(isGoal(currentNode.vector,goal)){
				return currentNode;
			}
			System.out.println("CurrentNode: "+currentNode);
			System.out.println("Despues del if");
			System.out.println("Open set:"+openSet);
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			ArrayList<Node>neighbors=expandNode(currentNode,goal);
			for(int i=0;i<neighbors.size();i++){
				Node neighbor=neighbors.get(i);
				tentative_g_score=currentNode.g+calculateHeuristicValue(currentNode.vector, neighbor.vector);
				if(closedSet.contains(neighbor)&&tentative_g_score>=neighbor.g){
					continue;
				}
				if(!openSet.contains(neighbor)|| tentative_g_score<neighbor.g){
					neighbor.g=tentative_g_score;
					neighbor.f=neighbor.g+calculateHeuristicValue(neighbor.vector, goal);
					if(!openSet.contains(neighbor)){
						openSet.add(neighbor);
					}
				}
			}
		}
		return null;	
	}
	public static double calculateHeuristicValue(Vector2 start, Vector2 goal){
		double value=start.dst2(goal);
		return value;
	}
	
	public static boolean isGoal(Vector2 currentPosition, Vector2 goal){
		float x=currentPosition.x-goal.x;
		float y=currentPosition.y-goal.y;
		if((Math.abs(x)<Constants.World.TILE_SIZE) ||(Math.abs(y)<Constants.World.TILE_SIZE)){
			return true;
		}
		return false;
		
	}
	
	public static ArrayList<Node> expandNode(Node n,Vector2 goal){
		ArrayList<Node>nodes=new ArrayList<Node>();
		Vector2 vector1=new Vector2(n.vector.x+Constants.World.TILE_SIZE,n.vector.y);
		Vector2 vector2=new Vector2(n.vector.x,n.vector.y+Constants.World.TILE_SIZE);
		Vector2 vector3=new Vector2(n.vector.x-Constants.World.TILE_SIZE,n.vector.y);
		Vector2 vector4=new Vector2(n.vector.x,n.vector.y-Constants.World.TILE_SIZE);
		Vector2 vector5=new Vector2(n.vector.x+Constants.World.TILE_SIZE,n.vector.y+Constants.World.TILE_SIZE);
		Vector2 vector6=new Vector2(n.vector.x-Constants.World.TILE_SIZE,n.vector.y+Constants.World.TILE_SIZE);
		Vector2 vector7=new Vector2(n.vector.x+Constants.World.TILE_SIZE,n.vector.y-Constants.World.TILE_SIZE);
		Vector2 vector8=new Vector2(n.vector.x-Constants.World.TILE_SIZE,n.vector.y-Constants.World.TILE_SIZE);
		nodes.add(new Node(vector1,n,n.f+calculateHeuristicValue(n.vector,vector1),calculateHeuristicValue(vector1,goal)));
		nodes.add(new Node(vector2,n,n.f+calculateHeuristicValue(n.vector,vector2),calculateHeuristicValue(vector2,goal)));;;
		nodes.add(new Node(vector3,n,n.f+calculateHeuristicValue(n.vector,vector3),calculateHeuristicValue(vector3,goal)));
		nodes.add(new Node(vector4,n,n.f+calculateHeuristicValue(n.vector,vector4),calculateHeuristicValue(vector4,goal)));
		nodes.add(new Node(vector5,n,n.f+calculateHeuristicValue(n.vector,vector5),calculateHeuristicValue(vector5,goal)));
		nodes.add(new Node(vector6,n,n.f+calculateHeuristicValue(n.vector,vector6),calculateHeuristicValue(vector6,goal)));
		nodes.add(new Node(vector7,n,n.f+calculateHeuristicValue(n.vector,vector7),calculateHeuristicValue(vector7,goal)));
		nodes.add(new Node(vector8,n,n.f+calculateHeuristicValue(n.vector,vector8),calculateHeuristicValue(vector8,goal)));

		return nodes;
		
	}
	
	public static ArrayList<Node> getPath (Vector2 start, Vector2 goal){
		 ArrayList<Node>pathNodes=new ArrayList<Node>();
		 Node path=AStar.calculateAStar(start, goal);
		  Node currentNode=path;
		  while(currentNode!=null){
			  pathNodes.add(0,currentNode);
			  currentNode=currentNode.parent;
		  }
		  return pathNodes;
	}
	public static void main(String[]args){
		float xgoal= 1837.5674f;
		float ygoal=333.29614f;
		Vector2 goal=new Vector2(xgoal, ygoal);
		float x=2185.6772f;
		float y=485.6722f;
		Vector2 initialPostion=new Vector2(x, y);
		
		Node path=AStar.calculateAStar(initialPostion, goal);
		System.out.println(path.parent);
	}
	
	
}
