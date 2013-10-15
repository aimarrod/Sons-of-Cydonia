package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class DaggerThrowSpell extends Spell{
	
	public DaggerThrowSpell(){
		this.icon = GraphicsLoader.load("dagger-throw-icon.png");
		this.tooltip = "\nDAGGER THROW:\n\n20 mana \n\nThrow a dagger to the front, which will return as a boomerang after traveling a short distance.\n\n Scales with strength.";
		this.cast = 0.2f;
		this.blocking = 0.4f;
		this.state = State.ATTACK;
		this.mana = 10;
	}

	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createDaggerThrow(group, pos, (int) (stats.strength*0.5f), pos.direction);
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}
}
