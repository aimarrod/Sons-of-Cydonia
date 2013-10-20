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
	public HudSystem parent;
	
	public CharacterMenu(HudSystem parent){
		container = new Texture(Gdx.files.internal("resources/character-menu.png"));
		skin = new Skin(  Gdx.files.internal( "resources/skin2.json" ) );
		font =skin.getFont("gameFont");
		this.parent = parent;

	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
        Stats stats = SoC.game.statsmapper.get(SoC.game.player);
        Player player = SoC.game.playermapper.get(SoC.game.player);
        
        batch.draw(container, getX(),  getY()-30, container.getWidth(), container.getHeight()+30);
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "HP: ", getX()+15, getY()+190);
        font.setScale(0.55f);
        font.setColor(Color.RED);
        font.draw(batch, ""+stats.maxHealth, getX()+70, getY()+190);
        font.setColor(skin.getColor("red"));
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "MP: ", getX()+15, getY()+170);
        font.setScale(0.55f);
        font.setColor(Color.BLUE);
        font.draw(batch, ""+stats.maxMana, getX()+70, getY()+170);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Str: ", getX()+15, getY()+150);
        font.setScale(0.55f);
        font.setColor(Color.ORANGE);
        font.draw(batch, stats.strength+"", getX()+70, getY()+150);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Agi: ", getX()+15, getY()+130);
        font.setScale(0.55f);
        font.setColor(0, 0.5f, 0, 1);
        font.draw(batch, stats.agility+"", getX()+70, getY()+130);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Int: ", getX()+15, getY()+110);
        font.setScale(0.55f);
        font.setColor(Color.CYAN);
        font.draw(batch, stats.intelligence+"", getX()+70, getY()+110);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Def: ", getX()+15, getY()+90);
        font.setScale(0.55f);
        font.setColor(Color.GRAY);
        font.draw(batch, stats.armor+"", getX()+70, getY()+90);
        
        font.setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Weapon: ", getX()+15, getY()+60);
        if(player.weapon != null){
        	batch.setColor(1,1,1,1);
        	batch.draw(player.weapon.icon, getX()+105, getY()+38, 25, 25);
        }
        
        font.setColor(Color.WHITE);
        font.draw(batch, "Armor: ", getX()+15, getY()+30);
        if(player.armor != null){
        	batch.setColor(1,1,1,1);
        	batch.draw(player.armor.icon, getX()+105, getY()+8, 25, 25);
        }
		
        font.setColor(Color.WHITE);
        font.draw(batch, "Level:", getX()+15, getY()+0);
        font.draw(batch, stats.level+"", getX()+105, getY()+0);        
        batch.setColor(1, 1, 1, 1);
	}
}


