package com.soc.game.components;

import java.util.ArrayList;

import com.artemis.Component;
import com.soc.ai.AI;
import com.soc.ai.Node;

public class Enemy extends Component{
	public AI processor;
	public int experience;
	
	public Enemy(int experience, AI processor){
		this.experience=experience;
		this.processor = processor;
	}
}
