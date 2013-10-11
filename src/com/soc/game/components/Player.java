package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.Input;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.objects.Armor;
import com.soc.objects.Item;
import com.soc.objects.Weapon;
import com.soc.utils.SavedPlayer;

public class Player extends Component {
	public int move_up;
	public int move_down;
	public int move_left;
	public int move_right;
	public int attack;
	public int inventory;
	public int characterMenu;
	public int gameMenu;
	public int [] spellkeys;
	public Weapon weapon;
	public Armor armor;
	public Item[] inventary;

	public Player(){
		move_up = Input.Keys.W;
		move_down = Input.Keys.S;
		move_left = Input.Keys.A;
		move_right = Input.Keys.D;
		attack = Input.Keys.SPACE;
		inventory=Input.Keys.I;
		characterMenu = Input.Keys.C;
		gameMenu=Input.Keys.ESCAPE;
		spellkeys = new int[]{Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4};
		inventary=new Item[Constants.Items.INVENTORY_SIZE];
		inventary[0]=SoC.game.objects[Constants.Items.AXE];
		inventary[1]=SoC.game.objects[Constants.Items.ARMOR];
		inventary[2]=SoC.game.objects[Constants.Items.HEALTH_POTION];
		inventary[3]=SoC.game.objects[Constants.Items.MANA_POTION];
		weapon=null;
		armor=null;
	}
	public Player(SavedPlayer player){
		move_up = player.move_up;
		move_down = player.move_down;
		move_left = player.move_left;
		move_right = player.move_right;
		attack = player.attack;
		inventory=player.inventory;
		characterMenu = player.characterMenu;
		gameMenu=player.gameMenu;
		this.spellkeys = player.spellkeys;
		this.inventary=new Item[Constants.Items.INVENTORY_SIZE];
		this.armor=(Armor) SoC.game.objects[player.armor];
		this.weapon=(Weapon)SoC.game.objects[player.weapon];
		for(int i=0;i<Constants.Items.INVENTORY_SIZE;i++){
			inventary[i]=SoC.game.objects[player.inventary[i]];
		}
	}
	public boolean addToInventary(Item item){
		boolean inserted=false;
		for(int i=0;i<inventary.length&&!inserted;i++){
			if(inventary[i]==null){
				inventary[i]=item;
				inserted=true;
			}
		}
		return inserted;
	}
	
	public boolean removeFromInventary(Item item){
		boolean removed=false;
		for(int i=0;i<inventary.length&&!removed;i++){
			if(inventary[i]==item){
				inventary[i]=null;
				removed=true;
			}
		}
		return removed;
	}
	
	public int numFreeSlots(){
		int freeSlots=0;
		for(int i=0;i<inventary.length;i++){
			if(inventary[i]==null){
				freeSlots++;
			}
		}
		return freeSlots;
	}
	
	
}
