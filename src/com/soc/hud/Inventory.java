package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.core.Constants;

public class Inventory extends Actor{
	public Texture slot;
	public Texture armorSlot;
	public Texture weaponSlot;
	public Inventory(){
		slot=new Texture(Gdx.files.internal("resources/slot.png"));
		armorSlot=new Texture(Gdx.files.internal("resources/armor-slot.png"));
		weaponSlot=new Texture(Gdx.files.internal("resources/slot-weapon.png"));
	}
	
	public void draw(SpriteBatch batch, float partenAlpha){
		int posX=0;
		int posY=256;
		for(int i=1;i<=Constants.Items.INVENTORY_SIZE;i++){
		batch.draw(slot, posX, posY,  64, 64 );
			posX+=64;
			if(i % 5==0){
				posY+=64;
				posX=0;
			}
		}
		batch.draw(armorSlot, 128, 512,  64, 64 );
		batch.draw(weaponSlot,128+64,512,64,64);
	}

}
