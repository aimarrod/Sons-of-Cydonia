package com.soc.utils;

import com.artemis.World;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.spells.DaggerThrowSpell;
import com.soc.game.spells.PunchSpell;
import com.soc.game.spells.Spell;

public class Globals {
	
	public static Position playerPosition;
	public static Stats playerStats;
	public static Player playerControls;
	public static World world;
	
	public static Spell[] spells;
	
	public static void initializeSpells(){
		spells = new Spell[Constants.Spells.SPELL_NUMBER];
		spells[Constants.Spells.DAGGER_THROW] = new DaggerThrowSpell(); 
		spells[Constants.Spells.PUNCH]=new PunchSpell();
	}
}
