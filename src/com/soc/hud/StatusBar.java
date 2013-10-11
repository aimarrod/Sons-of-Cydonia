package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class StatusBar extends Actor{
	
	public Texture container;
	public Texture bars;
	public BitmapFont font;
	public HudSystem parent;
	
	public StatusBar(HudSystem parent){
		bars = new Texture(Gdx.files.internal("resources/hp-mp.png"));
		container = new Texture(Gdx.files.internal("resources/bar-hp-mp.png"));
		font = new BitmapFont();
		this.parent = parent;
		parent.stage.addActor(this);
	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
        Stats stats = SoC.game.statsmapper.get(SoC.game.player);
		
        batch.draw(container, getX(),  getY()-15);
		
		float proportion = (float)stats.health / (float)stats.maxHealth;
		batch.draw(bars, getX() +3, getY() + 40, 0, 0, (int)(bars.getWidth()*proportion), 20);
		font.draw(batch, stats.health + "/" + stats.maxHealth, getX() + bars.getWidth()*0.40f, getY() + 56);
		proportion = (float)stats.mana / (float)stats.maxMana;
        batch.draw(bars, getX() +3, getY() + 14, 0, 20, (int)(bars.getWidth()*proportion), 20);
        font.draw(batch, stats.mana + "/" + stats.maxMana, getX() + bars.getWidth()*0.40f, getY() + 30);
        proportion=(float)stats.experience/(float)stats.maxExperience;
        batch.draw(bars, getX() +3, getY()-10, 0, 40, (int)(bars.getWidth()*proportion), (int)(13));
        font.setScale(0.9f);
        font.draw(batch, stats.experience + "/" + stats.maxExperience, getX() + bars.getWidth()*0.42f, getY()+5);
        font.setScale(1.0f);
	}
}
