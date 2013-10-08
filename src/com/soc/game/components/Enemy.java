package com.soc.game.components;

import java.util.ArrayList;

import com.artemis.Component;
import com.soc.ai.AI;
import com.soc.ai.Node;

public class Enemy extends Component{
	public AI processor;
	public float vision;
	public int expierence;
	
	public Enemy(float vision, int experience, AI processor){
		this.vision = vision;
		this.expierence=experience;
		this.processor = processor;
	}
}
