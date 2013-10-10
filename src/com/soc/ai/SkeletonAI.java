package com.soc.ai;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.spells.Spell;
import com.soc.utils.EffectsPlayer;

public class SkeletonAI implements AI {
	
	ArrayList<Node> path;
	
	public SkeletonAI(){
		path = new ArrayList<Node>();
	}

	@Override
	public void process(Entity e) {


			Enemy enemy = SoC.game.enemymapper.get(e);
			Position p = SoC.game.positionmapper.get(e);
			Velocity v = SoC.game.velocitymapper.get(e);
			State state = SoC.game.statemapper.get(e);
			Entity player = SoC.game.player;
			Position playp = SoC.game.positionmapper.get(player);

			float dstx = 0f;
			float dsty = 0f;
			boolean dstModified=false;
			
			if(state.state >= State.BLOCKED) return;
			
			

			if (Math.hypot(playp.x - p.x, playp.y - p.y) > enemy.vision) {
				v.vx = 0;
				v.vy = 0;
				state.state=State.IDLE;
			} else {
				dstModified=true;
				if (AStar.instance.isDirectPath(p, playp)) {
					path.clear();
					dstx = playp.x - p.x;
					dsty = playp.y - p.y;
				} else if (path.isEmpty()) {
					path = AStar.instance.getPath(p, playp);
				}

				if (!path.isEmpty()) {
					Node currentNode = path.get(0);
					dstx = Math.abs(currentNode.x - p.x);
					dsty = Math.abs(currentNode.y - p.y);
					if (dstx <= 16 && dsty <= 16) {
						path.remove(0);
					}
				}
			}

			if (Math.abs(dstx) < 16) {
				v.vx = 0;
			} else {
				if (dstx > 0) {
					v.vx = v.speed;
				} else {
					v.vx = -v.speed;
				}
			}
			if (Math.abs(dsty) < 16) {
				v.vy = 0;
			} else {
				if (dsty > 0) {
					v.vy = v.speed;
				} else {
					v.vy = -v.speed;
				}
			}
			if(dstModified && (Math.abs(dstx) < 16 && Math.abs(dsty) < 16 )){
				Spell spell = SoC.game.spells[Constants.Spells.PUNCH];
				state.state = spell.state;
				EffectsPlayer.play("skeleton-attack.ogg");
				e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS,spell.cast, spell.blocking, Constants.Spells.PUNCH));
				e.changedInWorld();

			}
			p.direction.x = Math.signum(v.vx);
			p.direction.y = Math.signum(v.vy);
		}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
