package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.objects.Armor;
import com.soc.objects.Item;
import com.soc.objects.Potion;
import com.soc.objects.Weapon;

public class Inventory extends Actor implements InputProcessor{
	public Texture slot;
	public Texture focusSlot;
	public Texture armorSlot;
	public Texture weaponSlot;
	public int width;
	public int height;
	public int focusedSlot;
	public Inventory(){
		slot=new Texture(Gdx.files.internal("resources/slot.png"));
		armorSlot=new Texture(Gdx.files.internal("resources/slot-armor.png"));
		weaponSlot=new Texture(Gdx.files.internal("resources/slot-weapon.png"));
		focusSlot=new Texture(Gdx.files.internal("resources/slot-weapon.png"));
		this.width=1280;
		this.height=900;
		focusedSlot=1;
	}
	
	public void draw(SpriteBatch batch, float partenAlpha){
		int posX=0;
		int posY=448;
		int posFocusX=0;
		int posFocusY=0;
		boolean existsFocus=false;
		Item itemFocused=null;
		for(int i=1;i<=Constants.Items.INVENTORY_SIZE;i++){
			if(i==focusedSlot){
				posFocusX=posX;
				posFocusY=posY;
				existsFocus=true;
			}else{
				batch.draw(slot, posX, posY,  64, 64 );
			}
			Item item=SoC.game.playermapper.get(SoC.game.player).inventary[i-1];
			if(item!=null){
				if(i==focusedSlot){
					itemFocused=item;
				}
				else
					batch.draw(item.icon,posX+5,posY+15,55,45);
			}
				posX+=64;
				if(i % 5==0){
					posY-=64;
					posX=0;
				}
		}
		if(existsFocus){
			batch.draw(slot, posFocusX, posFocusY,  70, 70 );
			if(itemFocused!=null){
				batch.draw(itemFocused.icon,posFocusX+5,posFocusY+15,61,51);
			}
		}
		batch.draw(armorSlot, 128, 512,  64, 64 );
		Armor armor=SoC.game.playermapper.get(SoC.game.player).armor;
		if(armor!=null){
			batch.draw(armor.icon,128+5,512+15,55,45);
		}
		batch.draw(weaponSlot,192,512,64,64);
		Weapon weapon=SoC.game.playermapper.get(SoC.game.player).weapon;
		if(weapon!=null){
			batch.draw(weapon.icon,192+5,512+15,55,45);
		}
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
		if(Gdx.input.isKeyPressed(Keys.TAB)){
			if(focusedSlot==20){
				focusedSlot=1;
			}else{
				focusedSlot++;
			}
		}else{
			if(Gdx.input.isKeyPressed(Keys.E)){
				Item item=SoC.game.playermapper.get(SoC.game.player).inventary[focusedSlot-1];
				if(item!=null){
					if(item instanceof Weapon){
						((Weapon)item).equip();
					}else{
						if(item instanceof Armor){
							((Armor)item).equip();
						}else{
							if(item instanceof Potion){
								((Potion)item).use();
							}
						}
					}
				}
			}
				
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (Gdx.input.isButtonPressed(Buttons.RIGHT)){
			if(screenX>0 && screenX<320 && screenY<height-256 && screenY>height-512){
				int posX=0;
				int posY=height-448;
				boolean foundSlot=false;
				for(int i=1;i<=Constants.Items.INVENTORY_SIZE &&!foundSlot;i++){
					if(screenX>posX && screenX<posX+64 && screenY<posY && screenY>posY-64){
						foundSlot=true;
						Item item=SoC.game.playermapper.get(SoC.game.player).inventary[i-1];
						if(item!=null){
							if(item instanceof Weapon){
								((Weapon)item).equip();
							}else{
								if(item instanceof Armor){
									((Armor)item).equip();
								}else{
									if(item instanceof Potion){
										((Potion)item).use();
									}
								}
							}
						}
					}
					posX+=64;
					if(i % 5==0){
						posY+=64;
						posX=0;
					}
				}
			}else{
				if(screenX>128 && screenX<192 && screenY<height-512 && screenY>height-576){
					Armor armor=SoC.game.playermapper.get(SoC.game.player).armor;
					if(armor!=null){
						armor.remove();
					}
				}else{
					if(screenX>192 && screenX<256 && screenY<height-512 && screenY>height-576){
						Weapon weapon=SoC.game.playermapper.get(SoC.game.player).weapon;
						if(weapon!=null){
							weapon.remove();
						}
					}
				}
				
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
		System.out.println("ScreenX: "+screenX);
		System.out.println("ScreenY: "+screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(screenX>0 && screenX<320 && screenY<height-256 && screenY>height-512){
			int posX=0;
			int posY=height-448;
			boolean foundSlot=false;
			for(int i=1;i<=Constants.Items.INVENTORY_SIZE &&!foundSlot;i++){
				if(screenX>posX && screenX<posX+64 && screenY<posY && screenY>posY-64){
					foundSlot=true;
					focusedSlot=i;
					Item item=SoC.game.playermapper.get(SoC.game.player).inventary[i-1];
					if(item!=null){
						SoC.game.world.getSystem(HudSystem.class).toggleTextButton(item.tooltip,posX,posY);
					}else{
						SoC.game.world.getSystem(HudSystem.class).toggleTextButton(null, 0,0);
					}
				}
				posX+=64;
				if(i % 5==0){
					posY+=64;
					posX=0;
				}
			}
		}
		else{
			focusedSlot=1;
			SoC.game.world.getSystem(HudSystem.class).toggleTextButton(null, 0,0);
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
