package com.soc.algorithms;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.math.Vector2;

public class Node implements Comparable {
	
	public Vector2 vector;
	public Node parent;
	public double g;
	public double f;
	
	public Node(Vector2 vector,Node parent,double g, double f){
		this.vector=vector;
		this.parent=parent;
		this.g=g;
		this.f=f;
	}
	@Override
	public int compareTo(Object arg0) {
		Node node2=(Node)arg0;
		if(this.f>node2.f){
			return 1;
		}else{
			if(this.f<node2.f){
				return -1;
			}
			return 0;
		}
	}
	
	public String toString(){
		return "X: "+vector.x+" Y: "+vector.y+" F:"+f;
		//return "Valor: "+f;
	}
	
//	public static void main(String []args){
//		Node node=new Node(null, null, 0,20);
//		Node node2=new Node(null, null, 0, 30);
//		Node node3=new Node(null,null,0, 50);
//		
//		ArrayList<Node>lista=new ArrayList<Node>();
//		lista.add(node2);
//		lista.add(node);
//		lista.add(node3);
//		Collections.sort(lista);
//		System.out.println(lista);
//	}
}
