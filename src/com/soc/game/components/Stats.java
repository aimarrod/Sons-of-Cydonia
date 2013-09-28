package com.soc.game.components;

import com.artemis.Component;

public class Stats extends Component{
	public int health;
	public int experience;
	public int level;
	
	public int armor;
	public int strength;
	public int agility;
	public int intelligence;

	public Stats(int health, int experience, int level, int armor, int strength, int agility, int intelligence){
		this.health = health;
		this.experience = experience;
		this.level = level;
		this.armor = armor;
		this.strength = strength;
		this.agility = agility;
		this.intelligence = intelligence;
	}
}
