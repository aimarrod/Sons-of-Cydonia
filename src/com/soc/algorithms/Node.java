package com.soc.algorithms;

import com.badlogic.gdx.math.Vector2;

public class Node implements Comparable {
	Vector2 vector;
	Node parent;
	double g;
	double f;
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
}
