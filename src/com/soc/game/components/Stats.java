package com.soc.game.components;

import com.artemis.Component;
import com.soc.core.Constants;
import com.soc.game.attacks.processors.DaggerThrowProcessor;
import com.soc.game.attacks.spells.Spell;

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
	
	public int attack;
	public int[] spells;
	public String clazz;
	
	public Stats(int health, int mana, int experience, int maxHealth,
			int maxMana, int maxExperience, int level, int armor, int strength,
			int agility, int intelligence, int attack, int[] spells, String clazz) {
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
		this.attack = attack;
		this.spells = spells;
		this.clazz = clazz;
	}
	
	public Stats() {
	}

	public boolean addExperience(int expGained){
		if(level >= 10){
			experience = maxExperience;
			return false;
		}
		experience+=expGained;
		boolean levelup = false;
		while(experience>=maxExperience){
			increaseLevel();
			levelup = true;
		}
		return levelup;
	}	
	private void increaseLevel(){
		level++;
		maxHealth += 20;
		health = maxHealth;
		maxMana += 10;
		mana = maxMana;
		armor += 1;
		strength += 5;
		agility += 5;
		intelligence += 5;
		experience -= maxExperience;
		maxExperience*=2;
		
		if(clazz.equals(Constants.Characters.WARRIOR)){
			if(level == 3){
				spells[1] = Constants.Spells.CHARGE;
			} else if(level == 5) {
				spells[2] = Constants.Spells.WHIRLBLADE;
			} else if(level == 8){
				spells[3] = Constants.Spells.QUAKEBLADE;
			}
		}
	}
}
