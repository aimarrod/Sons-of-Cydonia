package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.soc.core.Constants;
import com.soc.core.SoC;

public class Inventory extends Actor implements InputProcessor{
	public Texture slot;
	public Texture armorSlot;
	public Texture weaponSlot;
	public int width;
	public int height;
	public Inventory(){
		slot=new Texture(Gdx.files.internal("resources/slot.png"));
		armorSlot=new Texture(Gdx.files.internal("resources/slot-armor.png"));
		weaponSlot=new Texture(Gdx.files.internal("resources/slot-weapon.png"));
		SoC.game.inputMultiplexer.addProcessor(this);
		this.width=1280;
		this.height=900;
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
	
	public void updateRes(int witdh, int height){
		this.width=witdh;
		this.height=height;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Gdx.input.isButtonPressed(Buttons.RIGHT)){
			if(screenX>0 && screenX<320 && screenY<height-256 && screenY>height-512){
				//System.out.println("Pressed");
				System.out.println("ScreenX:" +screenX);
				System.out.println("ScreenY: "+screenY);
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
