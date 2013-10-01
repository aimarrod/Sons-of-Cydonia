package com.soc.game.components;

import com.artemis.Component;

public class Stats extends Component{
	public int health;
	public int mana;
	public int experience;
	
	public int maxHealth;
	public int maxMana;
	public int maxExperience;
	
	public int level;
	public int armor;
	public int strength;
	public int agility;
	public int intelligence;
	public Stats(int health, int mana, int experience, int maxHealth,
			int maxMana, int maxExperience, int level, int armor, int strength,
			int agility, int intelligence) {
		this.health = health;
		this.mana = mana;
		this.experience = experience;
		this.maxHealth = maxHealth;
		this.maxMana = maxMana;
		this.maxExperience = maxExperience;
		this.level = level;
		this.armor = armor;
		this.strength = strength;
		this.agility = agility;
		this.intelligence = intelligence;
	}
	
	public boolean addExperience(int expGained){
		experience+=expGained;
		if(experience>=maxExperience){
			increaseLevel();
			calculateNextLevel();
			return true;
		}
		return false;
	}	
	private void increaseLevel(){
		level++;
		armor+=2.5;
		strength+=5;
		agility+=5;
		intelligence+=5;
	}
	
	private void calculateNextLevel(){
		experience=0;
		maxExperience*=1.5;
	}
}
