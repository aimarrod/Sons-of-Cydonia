package com.soc.game.spells;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;

public class PunchSpell extends Spell{
	public PunchSpell(){
		this.icon = new TextureRegion(new Texture(Gdx.files.internal("resources/punch-icon.png")), 64, 64);
		this.tooltip = "Punch in the face!";
		this.cast = 1f;
		this.sound = "punch-swing.wav";
		this.blocking = 1f;
		this.state = State.ATTACK;
		this.sounddelay = 0.2f;
		this.mana = 0;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createPunch(group, pos, stats.strength, 16);	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	   	e.addToWorld();
	}

}
