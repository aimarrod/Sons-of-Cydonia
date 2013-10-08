package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.Input;
import com.soc.core.Constants;
import com.soc.objects.Armor;
import com.soc.objects.Item;
import com.soc.objects.Potion;
import com.soc.objects.Weapon;

public class Player extends Component {
	public int move_up;
	public int move_down;
	public int move_left;
	public int move_right;
	public int attack;
	public int inventory;
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
		spellkeys = new int[]{Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4};
		inventary=new Item[Constants.Items.INVENTORY_SIZE];
		inventary[0]=new Weapon("Axe","resources/axe.png","Best Axe Ever!",100,10,10);
		inventary[1]=new Armor("Armor","resources/armor.png", "Best Armor Ever!",100f);
		inventary[2]=new Potion("PotionHealth","resources/potion_health.png","Small Health Potion",100,0);
		inventary[3]=new Potion("ManaHealth","resources/potion_mana.png","Small Mana Potion",0,100);
		weapon=null;
		armor=null;
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
