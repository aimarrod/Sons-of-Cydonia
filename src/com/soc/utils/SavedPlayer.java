package com.soc.utils;

import com.soc.core.Constants;
import com.soc.game.components.Player;
import com.soc.objects.Item;

public class SavedPlayer {
	public int move_up;
	public int move_down;
	public int move_left;
	public int move_right;
	public int attack;
	public int inventory;
	public int characterMenu;
	public int gameMenu;
	public int [] spellkeys;
	public int weapon;
	public int armor;
	public int [] inventary;
	
	public SavedPlayer(Player p){
		this.move_up=p.move_up;
		this.move_down=p.move_down;
		this.move_left=p.move_left;
		this.move_right=p.move_right;
		this.attack=p.attack;
		this.inventory=p.inventory;
		this.characterMenu=p.characterMenu;
		this.gameMenu=p.gameMenu;
		this.spellkeys=p.spellkeys;
		if(p.armor==null){
			this.armor=0;
		}else{
			this.armor=p.armor.num;
		}
		if(p.weapon==null){
			this.weapon=0;
		}else{
			this.weapon=p.weapon.num;
		}
		inventary=new int [Constants.Items.INVENTORY_SIZE];
		Item item=null;
		for(int i=0;i<Constants.Items.INVENTORY_SIZE;i++){
			item=p.inventary[i];
			if(item==null)
				inventary[i]=0;
			else
				inventary[i]=item.num;
		}
	}

	public SavedPlayer(int move_up, int move_down, int move_left,
			int move_right, int attack, int inventory, int characterMenu,
			int gameMenu, int[] spellkeys, int weapon, int armor,
			int[] inventary) {
		super();
		this.move_up = move_up;
		this.move_down = move_down;
		this.move_left = move_left;
		this.move_right = move_right;
		this.attack = attack;
		this.inventory = inventory;
		this.characterMenu = characterMenu;
		this.gameMenu = gameMenu;
		this.spellkeys = spellkeys;
		this.weapon = weapon;
		this.armor = armor;
		this.inventary = inventary;
	}
	
	public SavedPlayer(){
		
	}
}
