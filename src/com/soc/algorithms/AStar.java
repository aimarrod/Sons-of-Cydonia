package com.soc.algorithms;
import java.util.ArrayList;
import java.util.Collections;

import com.artemis.utils.Bag;
import com.badlogic.gdx.math.Vector2;


public class AStar {
	
	public static Node calculateAStar(Vector2 start, Vector2 goal){
		Node finalNode;
		ArrayList<Node>closedSet=new ArrayList<Node>();
		ArrayList<Node>openSet=new ArrayList<Node>();
		//Bag<Node>cameFrom=new Bag<Node>();
		Node startNode=new Node(start,null,0,calculateHeuristicValue(start,goal));
		openSet.add(startNode);
		Node currentNode;
		double tentative_g_score=0;
		while(!openSet.isEmpty()){
			Collections.sort(openSet);
			currentNode=openSet.get(0);
			if(currentNode.vector.equals(goal)){
				return currentNode;
			}
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
	
	public static ArrayList<Node> expandNode(Node n,Vector2 goal){
		ArrayList<Node>nodes=new ArrayList<Node>();
		Vector2 vector1=new Vector2(n.vector.x+1,n.vector.y);
		Vector2 vector2=new Vector2(n.vector.x,n.vector.y+1);
		Vector2 vector3=new Vector2(n.vector.x-1,n.vector.y);
		Vector2 vector4=new Vector2(n.vector.x,n.vector.y-1);
		Vector2 vector5=new Vector2(n.vector.x+1,n.vector.y+1);
		Vector2 vector6=new Vector2(n.vector.x-1,n.vector.y+1);
		Vector2 vector7=new Vector2(n.vector.x+1,n.vector.y-1);
		Vector2 vector8=new Vector2(n.vector.x-1,n.vector.y-1);
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
	
	
	
}
