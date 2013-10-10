package com.soc.game.spells;

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

public class SlashSpell extends Spell{
		public SlashSpell(){
			this.icon = new TextureRegion(new Texture(Gdx.files.internal("resources/slash-icon.png")), 64, 64);
			this.tooltip = "Slash your evil foe with your mighty sword!";
			this.cast = 0.3f;
			this.blocking = 0.4f;
			this.state = State.ATTACK;
			this.sound = "sword-swing.wav";
			this.sounddelay = 0.1f;
			this.mana = 0;
		}
		@Override
		public void create(Entity source, String group, Position pos, Stats stats) {
			Entity e = EntityFactory.createSlash(pos, stats.strength);	
		    SoC.game.groupmanager.add(e, group);
		    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
		    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
		   	e.addToWorld();
		}
}
