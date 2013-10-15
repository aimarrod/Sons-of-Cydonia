package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class TentacleSpell extends Spell{

	
	public TentacleSpell(){
		this.icon = GraphicsLoader.load("arrow-icon.png");
		this.tooltip = "Summon a water tentacle from the underground";
		this.cast = 0.0f;
		this.blocking = Constants.Spells.TENTACLES_DURATION;
		this.state = State.ATTACK;
		this.mana = 0;
	}

	@Override
	public void create(Entity source, String group, Position p, Stats stats) {
		Position pos = SoC.game.positionmapper.get(SoC.game.player);
		Entity e = EntityFactory.createTentacles(pos.x, pos.y, pos.z, (int) (stats.intelligence));
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}
}
