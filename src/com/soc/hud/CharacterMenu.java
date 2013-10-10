package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.soc.core.SoC;
import com.soc.game.components.Player;
import com.soc.game.components.Stats;

public class CharacterMenu extends Actor{
	
	Texture container;
	BitmapFont font;
	Skin skin;
	
	public CharacterMenu(){
		container = new Texture(Gdx.files.internal("resources/character-menu.png"));
		skin = new Skin(  Gdx.files.internal( "resources/skin2.json" ) );
		font =skin.getFont("gameFont");

	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
        Stats stats = SoC.game.statsmapper.get(SoC.game.player);
        Player player = SoC.game.playermapper.get(SoC.game.player);
        
        batch.draw(container, getX(),  getY());
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Health: ", getX()+15, getY()+190);
        font.setScale(0.55f);
        font.setColor(Color.RED);
        font.draw(batch, ""+stats.maxHealth, getX()+70, getY()+190);
        font.setColor(skin.getColor("red"));
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Mana: ", getX()+15, getY()+170);
        font.setScale(0.55f);
        font.setColor(Color.BLUE);
        font.draw(batch, ""+stats.maxMana, getX()+70, getY()+170);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Strength: ", getX()+15, getY()+150);
        font.setScale(0.55f);
        font.setColor(Color.ORANGE);
        font.draw(batch, stats.strength+"", getX()+110, getY()+150);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Agility: ", getX()+15, getY()+130);
        font.setScale(0.55f);
        font.setColor(0, 0.5f, 0, 1);
        font.draw(batch, stats.agility+"", getX()+110, getY()+130);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Intelligence: ", getX()+15, getY()+110);
        font.setScale(0.55f);
        font.setColor(Color.CYAN);
        font.draw(batch, stats.intelligence+"", getX()+110, getY()+110);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Defense: ", getX()+15, getY()+90);
        font.setScale(0.55f);
        font.setColor(Color.GRAY);
        font.draw(batch, stats.armor+"", getX()+110, getY()+90);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Weapon: ", getX()+15, getY()+60);
        if(player.weapon != null){
        	batch.setColor(1,1,1,1);
        	batch.draw(player.weapon.icon, getX()+85, getY()+38, 25, 25);
        }
        
        font.setColor(Color.WHITE);
        font.draw(batch, "Armor: ", getX()+15, getY()+30);
        if(player.armor != null){
        	batch.setColor(1,1,1,1);
        	batch.draw(player.armor.icon, getX()+85, getY()+8, 25, 25);
        }
		
        batch.setColor(1, 1, 1, 1);
	}
}

