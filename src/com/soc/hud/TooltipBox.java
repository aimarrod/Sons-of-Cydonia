package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.soc.core.SoC;
import com.soc.utils.GraphicsLoader;

public class TooltipBox extends Actor{
	String text;
	float timer;
	private Texture container;
	private Skin skin;
	private BitmapFont font;
	public HudSystem parent;
	
	public TooltipBox(HudSystem parent){
		this.text = null;
		this.timer = 0;
		this.container = GraphicsLoader.load("dialog-box.png");
		skin = new Skin(  Gdx.files.internal( "resources/skin2.json" ) );
		this.font =skin.getFont("gameFont");
		this.parent = parent;
		parent.stage.addActor(this);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		if(text == null) return;
		batch.setColor(1, 1, 1, timer);
		font.setColor(1, 1, 1, timer);
		font.setScale(0.45f);
		timer += SoC.game.world.delta*0.5;
		if(timer > 1){
			timer = 1;
		}
		batch.draw(container, getX(), getY());
		font.drawWrapped(batch, text, getX()+10, getY()+container.getHeight()-10, getWidth());
		batch.setColor(1, 1, 1, 1);
		
	}
	
	public void setText(String text, float appearTime){
		if(this.text == text) return;
		this.text = text;
		this.timer = 1 - appearTime;
	}
}
