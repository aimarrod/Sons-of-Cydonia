package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class IceConeSpell extends Spell {
	public IceConeSpell(){
		this.icon = GraphicsLoader.load("ice-cone-icon.png");
		this.tooltip = "Ice Cone\n35 mana\nThrow icicles in a cone in front of you, dealing normal damage with each of them";
		this.cast = 0.3f;
		this.blocking = 0.4f;
		this.state = State.ATTACK;
		this.mana = 35;
	}

	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		if(pos.direction.x != 0){
			pos.direction.y = 0;
			Vector2 dir = pos.direction.cpy();
			dir.y = -0.3f;
			while(dir.y < 0.3f){
				Entity e = EntityFactory.createIcicle(pos, (int) (stats.intelligence), dir);
			    SoC.game.groupmanager.add(e, group);
			    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
			    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
			    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
			   	e.addToWorld();
			   	dir.y += 0.1f;
			}
		} else if(pos.direction.y != 0){
			Vector2 dir = pos.direction.cpy();
			dir.x = -0.3f;
			while(dir.x < 0.3f){
				Entity e = EntityFactory.createIcicle(pos, (int) (stats.intelligence), dir);
			    SoC.game.groupmanager.add(e, group);
			    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
			    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
			    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
			   	e.addToWorld();
			   	dir.x += 0.1f;
			}
		}
	}
}
