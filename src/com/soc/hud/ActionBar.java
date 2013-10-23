package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Buff;
import com.soc.game.components.Delay;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.states.benefits.Shield;
import com.soc.utils.FloatingText;
import com.soc.utils.GraphicsLoader;

public class ActionBar extends Actor implements InputProcessor{

	public HudSystem parent;
	int[] spells;
	int tooltip;
	public Texture empty, menu;
	BitmapFont font;
	
	public ActionBar(HudSystem parent){
		this.parent = parent;
		font=SoC.game.skin.getFont("menuFont");
		parent.stage.addActor(this);
		empty = GraphicsLoader.load("empty-potion.png");
		menu = GraphicsLoader.load("menu-icon.png");
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		spells = SoC.game.statsmapper.get(SoC.game.player).spells;
		font.setScale(1.0f);
		for(int i = 0; i < spells.length; i++){
			if(spells[i] != Constants.Spells.NO_SPELL){
				batch.draw(SoC.game.spells[spells[i]].icon, getX()+(i*64+5), getY()+7);
				font.draw(batch, i+1+"", getX()+(i*64+53),  getY()+63);
			}
		}
		
		font.setColor(1, 1, 1, 1);
		font.setScale(0.5f);

		String clazz = SoC.game.statsmapper.get(SoC.game.player).clazz;
		if(clazz.equals(Constants.Characters.WARRIOR)){
			batch.draw(GraphicsLoader.load("warrior-attack-icon.png"), getX()+10, getY()+90, 35, 35);
		} else if(clazz.equals(Constants.Characters.MAGE)){
			batch.draw(GraphicsLoader.load("mage-attack-icon.png"), getX()+10, getY()+90, 35, 35);
		}
		font.draw(batch, "SPACE", getX()+10, getY()+90);
		
		if(SoC.game.playermapper.get(SoC.game.player).blocking){
			batch.draw(GraphicsLoader.load("shield-active-icon.png"), getX()+50, getY()+90, 35, 35);
		} else {
			batch.draw(GraphicsLoader.load("shield-inactive-icon.png"), getX()+50, getY()+90, 35, 35);
		}
		font.draw(batch, "SHIFT", getX()+55, getY()+90);
		
		TextureRegion potion = parent.inventory.getFirstHealthPotion();
		if(potion != null){
			batch.draw(potion, getX() + 90, getY()+90, 40, 40);
		} else {
			batch.draw(empty, getX() + 90, getY()+90, 40, 40);
		}
		font.draw(batch, "Q", getX()+106, getY()+90);
		
		potion = parent.inventory.getFirstManaPotion();
		if(potion != null){
			batch.draw(potion, getX() + 130, getY()+90, 40, 40);
		} else {
			batch.draw(empty, getX() + 130, getY()+90, 40, 40);
		}
		font.draw(batch, "E", getX()+146, getY()+90);
		
		if(parent.inventory.hasParent()){
			batch.draw(GraphicsLoader.load("inventory-open.png"), getX()+170, getY()+90, 35, 35);
		} else{
			batch.draw(GraphicsLoader.load("inventory-closed.png"), getX()+170, getY()+90, 35, 35);
		}
		font.draw(batch, "I", getX()+186, getY()+90);
		
		batch.draw(menu, getX() + 210, getY()+92, 35, 35);
		font.setScale(0.5f);
		font.draw(batch, "ESC", getX()+215, getY()+90);
		
		font.setColor(1, 1, 1, 1);
		font.setScale(1.0f);
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
		Vector2 coords = parent.stage.stageToScreenCoordinates(new Vector2(screenX, screenY));
		
		spells = SoC.game.statsmapper.get(SoC.game.player).spells;
		if(coords.x > getX() && coords.x < getX() + (64+2)*spells.length && coords.y > getY() && coords.y < getY() + 64){
			for(int i = 0; i < spells.length; i++){
				if(coords.x > getX()+(64+2)*(i) && coords.x < (64+2)*(i+1) && coords.y > getY() && coords.y < getY() + 64){
					if(spells[i] == Constants.Spells.NO_SPELL) return false;
					
					State state = SoC.game.statemapper.get(SoC.game.player);
					Velocity vel = SoC.game.velocitymapper.get(SoC.game.player);
					Stats stats = SoC.game.statsmapper.get(SoC.game.player);
					Position pos = SoC.game.positionmapper.get(SoC.game.player);
					
					Spell spell = SoC.game.spells[spells[i]];
					if(stats.mana < spell.mana){
						FloatingText text = new FloatingText("No mana!", 1f, pos.x, pos.y, 50);
						text.r = 0.5f;
						text.b = 1;
						text.g = 0.5f;
						SoC.game.renderSystem.texts.add(text);
						return true;
					}
					stats.mana -= spell.mana;
					state.state = spell.state;
					SoC.game.player.addComponent(new Delay(Constants.Groups.PLAYER_ATTACKS, spell.cast, spell.blocking, spells[i]));
					SoC.game.player.changedInWorld();
					vel.vx = 0;
					vel.vy = 0;
					return true;
				}
			}
		}

		if(coords.x > getX()+10 && coords.x < getX() + 50 && coords.y > getY()+90 && coords.y < getY() + 144){
			State state = SoC.game.statemapper.get(SoC.game.player);
			Velocity vel = SoC.game.velocitymapper.get(SoC.game.player);
			int spellnum = SoC.game.statsmapper.get(SoC.game.player).attack;
			Spell spell = SoC.game.spells[spellnum];
			state.state = spell.state;
			SoC.game.player.addComponent(new Delay(Constants.Groups.PLAYER_ATTACKS, spell.cast, spell.blocking, spellnum));
			SoC.game.player.changedInWorld();
			vel.vx = 0;
			vel.vy = 0;
			return true;
		} else if(coords.x > getX()+50 && coords.x < getX() + 90 && coords.y > getY()+90 && coords.y < getY() + 144){
			State state = SoC.game.statemapper.get(SoC.game.player);
			Velocity vel = SoC.game.velocitymapper.get(SoC.game.player);
			Stats stats = SoC.game.statsmapper.get(SoC.game.player);
			Player controls = SoC.game.playermapper.get(SoC.game.player);
			Position pos = SoC.game.positionmapper.get(SoC.game.player);
			
			if(!controls.blocking){
				if(stats.mana <= 0){
					FloatingText text = new FloatingText("No mana!", 1f, pos.x, pos.y, 50);
					text.r = 0.5f;
					text.b = 1;
					text.g = 0.5f;
					SoC.game.renderSystem.texts.add(text);
					return true;
				}
				Buff.addbuff(SoC.game.player, new Shield());
				vel.vx = 0;
				vel.vy = 0;
				state.state = State.IDLE;
				controls.blocking = true;
				return true;
			} else {
				SoC.game.buffmapper.get(SoC.game.player).removebuff(Shield.class,SoC.game.player);
				controls.blocking = false;
				return true;
			}
		} else if(coords.x > getX()+90 && coords.x < getX() + 130 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.inventory.useFirstHealthPotion();
			return true;
		} else if(coords.x > getX()+130 && coords.x < getX() + 170 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.inventory.useFirstManaPotion();
			return true;
		} else if(coords.x > getX()+170 && coords.x < getX() + 210 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.toggleInventory();
			return true;
		}  else if(coords.x > getX()+210 && coords.x < getX() + 250 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.toogleGameMenu();
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Vector2 coords = parent.stage.stageToScreenCoordinates(new Vector2(screenX, screenY));
		spells = SoC.game.statsmapper.get(SoC.game.player).spells;
		if(coords.x > getX() && coords.x < getX() + (64+2)*spells.length && coords.y > getY() && coords.y < getY() + 64){
			for(int i = 0; i < spells.length; i++){
				if(coords.x > getX()+(64+2)*(i) && coords.x < (64+2)*(i+1) && coords.y > getY() && coords.y < getY() + 64){
					if(spells[i] == Constants.Spells.NO_SPELL) return false;
					parent.tooltip.setText(SoC.game.spells[spells[i]].tooltip, 0.2f);
					return false;
				}
			}
		}
		if(coords.x > getX()+10 && coords.x < getX() + 50 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.tooltip.setText("Normal attack (SPACE)", 0);
			return false;
		} else if(coords.x > getX()+50 && coords.x < getX() + 90 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.tooltip.setText("Shield (SHIFT)", 0);
			return false;
		} else if(coords.x > getX()+90 && coords.x < getX() + 130 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.tooltip.setText("Use health potion (Q)", 0);
			return false;
		} else if(coords.x > getX()+130 && coords.x < getX() + 170 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.tooltip.setText("Use mana potion (E)", 0);
			return false;
		} else if(coords.x > getX()+170 && coords.x < getX() + 210 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.tooltip.setText("Toggle inventory (I)", 0);
			return false;
		} else if(coords.x > getX()+210 && coords.x < getX() + 250 && coords.y > getY()+90 && coords.y < getY() + 144){
			parent.tooltip.setText("Open menu (ESC)", 0);
			return false;
		}

		parent.tooltip.setText(null, 0);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
