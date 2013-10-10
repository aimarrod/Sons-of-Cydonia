package com.soc.game.spells;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.GraphicsLoader;

public class ChargeSpell extends Spell{
	
	public ChargeSpell(){
		this.icon = GraphicsLoader.load("charge-icon.png");
		this.tooltip = "Charges Forward";
		this.cast = 0f;
		this.blocking = Constants.Spells.CHARGE_DURATION+this.cast;
		this.state = State.CHARGING;
		this.mana = 30;
	}
	
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createCharge(source ,group, pos, (int)(stats.strength*0.66f));	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
	   	e.addToWorld();
		
	}
	
	
}
