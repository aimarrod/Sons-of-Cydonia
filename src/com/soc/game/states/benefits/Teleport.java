package com.soc.game.states.benefits;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.GraphicsLoader;

public class Teleport implements Benefit{
	public float timer;
	public AnimatedRenderer renderer; 
	public float posx, posy;
	public int posz;
	public boolean sounded;
	
	public Teleport(float posx, float posy, int posz){
		timer=Constants.Buff.TELEPORT_CAST_TIME;
		renderer=GraphicsLoader.loadTeleport();
		this.posx = posx;
		this.posy = posy;
		this.posz = posz;
	}
	@Override
	public void process(Entity e) {
		if(!sounded){
			EffectsPlayer.play("teleport.ogg");
			sounded = true;
		}
		timer -= SoC.game.world.delta;
		if(timer <= 0){
			Position pos = SoC.game.positionmapper.get(e);
			pos.x = posx;
			pos.y = posy;
			pos.z = posz;
			SoC.game.buffmapper.get(e).removebuff(e,this);
		}
	}
	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
