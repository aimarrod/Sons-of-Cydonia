package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.osc.game.benefits.Inmune;
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
	boolean avatarSpawned, fighting;
	public Random r;
	int topTile, bottomTile, leftTile, rigthTile;
	
	public GaiaAI(){
		timer = 0;
		r = new Random();
		topTile = 53;
		bottomTile = 14;
		leftTile = 114;
		rigthTile = 114+78;
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		
		if(fighting){
			if(timer <= 0){
				boolean left = false;
				for(float i = bottomTile; i < topTile - bottomTile; i += 3.5f){
					Entity tornado = EntityFactory.createTornado(
							((left)?(leftTile):(rigthTile))*World.TILE_SIZE + World.TILE_SIZE*0.5f, 
							bottomTile + (i * World.TILE_SIZE) + World.TILE_SIZE*0.5f,
							SoC.game.positionmapper.get(SoC.game.player).z, 
							((left)?new Vector2(1, 0):new Vector2(-1,0)),
							1f);
				    SoC.game.groupmanager.add(tornado, Constants.Groups.ENEMY_ATTACKS);
				    SoC.game.groupmanager.add(tornado, Constants.Groups.MAP_BOUND);
				    SoC.game.groupmanager.add(tornado, Constants.Groups.PROJECTILES);
				    SoC.game.levelmanager.setLevel(tornado, Constants.Groups.LEVEL+SoC.game.positionmapper.get(SoC.game.player).z);
				    tornado.addToWorld();
				    left = !left;
				}
				timer = 1.5f;
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
			return;
		}
		
		if(!(SoC.game.progress.gaiaAirDefeated && SoC.game.progress.gaiaDarkDefeated && SoC.game.progress.gaiaFlameDefeated)) 
			return;
		else {
			if(!SoC.game.progress.gaiaAvatarDefeated && !avatarSpawned){
				Entity gaia = EntityFactory.createGaiaAvatar(158*World.TILE_SIZE, 40*World.TILE_SIZE, 0);
				SoC.game.groupmanager.add(gaia, Constants.Groups.MAP_BOUND);
				SoC.game.levelmanager.setLevel(gaia, Constants.Groups.LEVEL +0);
				SoC.game.groupmanager.add(gaia, Constants.Groups.ENEMIES);
				SoC.game.groupmanager.add(gaia, Constants.Groups.CHARACTERS);
				gaia.addToWorld();
				
				avatarSpawned = true;
			} else if(SoC.game.progress.gaiaAvatarDefeated){
					SoC.game.buffmapper.get(e).removebuff(Inmune.class);
					fighting = true;
					return;
			}
		}
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaDefeated=true;
	}

}
