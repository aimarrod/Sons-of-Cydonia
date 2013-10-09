package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class StatusBar extends Actor{
	
	public Texture container;
	public Texture bars;
	
	public StatusBar(){
		bars = new Texture(Gdx.files.internal("resources/hp-mp.png"));
		container = new Texture(Gdx.files.internal("resources/bar-hp-mp.png"));
	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
        Stats stats = SoC.game.statsmapper.get(SoC.game.player);
		
		//batch.draw(container, getX(),  getY());
        batch.draw(container, getX(),  getY()-15);
		
		float proportion = (float)stats.health / (float)stats.maxHealth;
		//batch.draw(region, x, y, originX, originY, width, height);
        //batch.draw(bars, getX() +3, getY() + 34, 0, 0, (int)(bars.getWidth()*proportion), (int)(bars.getHeight()*0.33));
		batch.draw(bars, getX() +3, getY() + 40, 0, 0, (int)(bars.getWidth()*proportion), 20);
		proportion = (float)stats.mana / (float)stats.maxMana;
        batch.draw(bars, getX() +3, getY() + 14, 0, 20, (int)(bars.getWidth()*proportion), 20);
        proportion=(float)stats.experience/(float)stats.maxExperience;
        batch.draw(bars, getX() +3, getY()-10, 0, 40, (int)(bars.getWidth()*proportion), (int)(13));

	}
}
