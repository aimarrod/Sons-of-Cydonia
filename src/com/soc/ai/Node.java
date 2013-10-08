package com.soc.ai;

	import java.util.ArrayList;
	import java.util.Collections;

	import com.badlogic.gdx.math.Vector2;

	public class Node implements Comparable {
		
		public int x,y;
		public Node parent;
		public double g;
		public double f;
		
		public Node(int x,int y,Node parent,double g, double f){
			this.x=x;
			this.y=y;
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
			return x+""+y;
		}
		
		public String id(){
			return x+""+y;
		}
		
		@Override
		public boolean equals(Object o){
			try{
				Node other = (Node) o;
				return (other.x==x && other.y==y);
			} catch(ClassCastException e){
				return false;
			}
			
		}

	}
