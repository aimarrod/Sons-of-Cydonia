package com.soc.game.components;

import java.util.ArrayList;

import com.artemis.Component;
import com.soc.algorithms.Node;

public class Enemy extends Component{
	public ArrayList<Node>path=new ArrayList<Node>();
	public float vision;
	public int expierence;
	
	public Enemy(float vision, int experience){
		this.vision = vision;
		this.expierence=experience;
	}
}
