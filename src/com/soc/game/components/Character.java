package com.soc.game.components;


import com.artemis.Component;
import com.soc.game.graphics.Renderer;

public class Character extends Component{
	public String damageSound;
	public String deathSound;
	
	public Renderer[] renderers;
	public float deathTime;
	
	public Character(){
		this.renderers = new Renderer[State.STATENUM]; 
	}
}
