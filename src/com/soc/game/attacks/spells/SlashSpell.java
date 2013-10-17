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

public class SlashSpell extends Spell{
		public SlashSpell(){
			this.icon = GraphicsLoader.load("slash-icon.png");
			this.tooltip = "Slash your evil \nfoe with your \nmighty sword!";
			this.cast = 0.2f;
			this.blocking = 0.35f;
			this.state = State.ATTACK;
			this.sound = "sword-swing.wav";
			this.sounddelay = 0.1f;
			this.mana = 0;
		}
		@Override
		public void create(Entity source, String group, Position pos, Stats stats) {
			Entity e = EntityFactory.createSlash(source, pos, (int) (stats.strength * 0.7f + stats.agility * 0.4f));	
		    SoC.game.groupmanager.add(e, group);
		    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
		    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
		   	e.addToWorld();
		}
}
