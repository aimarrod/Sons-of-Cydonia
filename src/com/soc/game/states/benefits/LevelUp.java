package com.soc.game.states.benefits;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.FloatingText;
import com.soc.utils.GraphicsLoader;

public class LevelUp implements Benefit{
	public float timer;
	public boolean sounded;
	public AnimatedRenderer renderer;
	
	public LevelUp(){
		this.timer=1f;
		renderer = GraphicsLoader.loadLevelUp();
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
