package com.soc.utils;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Expires;
import com.soc.game.components.Feet;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Character;
import com.soc.game.components.Velocity;
import com.soc.game.spells.DaggerThrowSpell;
import com.soc.game.spells.PunchSpell;
import com.soc.game.spells.Spell;

public class Globals {
	
	public static Position playerPosition;
	public static Stats playerStats;
	public static Player playerControls;
	public static World world;
	
	public static ComponentMapper<Position> positionmapper;
	public static ComponentMapper<Feet> feetmapper;
	public static ComponentMapper<Bounds> boundsmapper;
	public static ComponentMapper<Stats> statsmapper;
	public static ComponentMapper<Player> playermapper;
	public static ComponentMapper<Enemy> enemymapper;
	public static ComponentMapper<Expires> expiresmapper;
	public static ComponentMapper<Character> charactermapper;
	public static ComponentMapper<State> statemapper;
	public static ComponentMapper<Velocity> velocitymapper;
	public static ComponentMapper<Delay> delaymapper;
	public static ComponentMapper<Attack> attackmapper;
	//public static ComponentMapper<Component>
	
	public static Spell[] spells;
	
	public static void initializeSpells(){
		spells = new Spell[Constants.Spells.SPELL_NUMBER];
		spells[Constants.Spells.DAGGER_THROW] = new DaggerThrowSpell(); 
		spells[Constants.Spells.PUNCH]=new PunchSpell();
	}
	
	public static void initializeWorld(World world){
		Globals.world = world;
		positionmapper = world.getMapper(Position.class);
		feetmapper = world.getMapper(Feet.class);
		boundsmapper = world.getMapper(Bounds.class);
		statsmapper = world.getMapper(Stats.class);
		playermapper = world.getMapper(Player.class);
		expiresmapper = world.getMapper(Expires.class);
		charactermapper = world.getMapper(Character.class);
		statemapper = world.getMapper(State.class);
		velocitymapper = world.getMapper(Velocity.class);
		delaymapper = world.getMapper(Delay.class);
		attackmapper = world.getMapper(Attack.class);
	}
}
