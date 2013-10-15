package com.soc.game.attacks.spells;

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
		this.tooltip = "\nCHARGE\n\n30 mana\n\nRushers forward with a spear, damaging and pushing all enemies on front of you.\nScales with strength and a little agility";
		this.cast = 0f;
		this.blocking = Constants.Spells.CHARGE_DURATION+this.cast;
		this.state = State.CHARGING;
		this.mana = 30;
	}
	
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createCharge(source ,group, pos, (int)(stats.strength*0.66f + stats.agility*0.3) );	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
	   	e.addToWorld();
		
	}
	
	
}
