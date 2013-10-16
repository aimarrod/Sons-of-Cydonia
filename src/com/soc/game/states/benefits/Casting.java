package com.soc.game.states.benefits;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class Casting implements Benefit{
	public float timer;
	public float gainHealth;
	public boolean buffAdded;
	public int initialHealth;
	public AnimatedRenderer renderer;
	public Casting(float timer, String color){
		this.timer=timer;
		buffAdded=false;
		if(color.equals(Constants.BuffColors.RED))
			renderer=GraphicsLoader.loadRedCast();
		if(color.equals(Constants.BuffColors.DARK))
			renderer=GraphicsLoader.loadDarkCast();
	}
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		if(timer<=0)
			SoC.game.buffmapper.get(e).removebuff(e,this);
	}
	@Override
	public void delete(Entity e) {		
	}
}
