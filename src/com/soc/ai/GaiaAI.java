package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.alterations.Push;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;

public class GaiaAI implements AI{

	public float timer;
	boolean avatarSpawned;
	public Random r;
	
	public GaiaAI(){
		timer = 0;
		r = new Random();
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		if(!(SoC.game.progress.gaiaAirDefeated && SoC.game.progress.gaiaDarkDefeated && SoC.game.progress.gaiaFlameDefeated)){
			if(SoC.game.damagemapper.has(e)){
				e.getComponent(Damage.class).damage = 0;
				SoC.game.hudSystem.tooltip.pop("An eerie voice resonates: I am Gaia, keeper of this temple. Find and defeat my aspects if thou wish to continue.", 0, 7f);
			}
			return;
		} else {
			if(!avatarSpawned){
				//Spawn avatar
				avatarSpawned = true;
			}
			if(SoC.game.damagemapper.has(e)){
				Position pos = SoC.game.positionmapper.get(e);
				Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
				Push p = new Push(new Vector2(), 1650, 1500);
				float dstx = playerPos.x - pos.x;
				float dsty = playerPos.y - pos.y;
				if(Math.abs(dstx) > 16) p.direction.x = Math.signum(dstx);
				if(Math.abs(dsty) > 16) p.direction.y = Math.signum(dsty);
				if(p.direction.x != 0 && p.direction.y != 0) p.distance = 2500;
				Debuff.addDebuff(SoC.game.player, p);
			}
		}
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaDefeated=true;
	}

}
