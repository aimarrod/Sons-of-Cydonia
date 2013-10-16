package com.soc.utils;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.soc.core.SoC;
import com.soc.hud.HudSystem;

public class DialogBox {
	public Queue<String> text;
	public Queue<Vector2> positions;
	float timer;
	private Skin skin;
	private BitmapFont font;
	private float duration;
	private boolean dissapearing;
	private float lowerBound, upperBound, leftBound, rightBound;
	
	public DialogBox(){
		this.text = new LinkedList<String>();
		this.positions = new LinkedList<Vector2>();
		this.timer = 0;
		skin = new Skin(  Gdx.files.internal( "resources/skin2.json" ) );
		this.font =skin.getFont("gameFont");

	}
	
	public void draw (SpriteBatch batch) {
		if(text.isEmpty()) return;
		batch.setColor(1, 1, 1, timer);
		font.setColor(1, 1, 1, timer);
		font.setScale(0.33f);
		timer += SoC.game.world.delta*((dissapearing)?-1:1);
		if(timer > 1){
			timer = 1;
		} else {
			if(timer < 0){
				timer = 0;
			}
		}
		
		lowerBound = SoC.game.camera.position.y - SoC.game.camera.viewportHeight * 0.5f;
		upperBound = SoC.game.camera.position.y + SoC.game.camera.viewportHeight * 0.5f;
		leftBound = SoC.game.camera.position.x - SoC.game.camera.viewportWidth * 0.5f;
		rightBound = SoC.game.camera.position.x + SoC.game.camera.viewportWidth * 0.5f;
		
		
		float posX = positions.peek().x;
		float posY = positions.peek().y;
		float width = 300;
		
		if(posX < leftBound){
			posX = leftBound + 50;
			width = 100;
		} else if(posX + 300 > rightBound){
			posX = rightBound - 350;
			width = 100;
		}
		
		if(posY < lowerBound - 400 + width){
			posY = lowerBound + 50;
		} else if(posY > upperBound){
			posY = upperBound - 350;
		}
		
		font.drawWrapped(batch, text.peek(), posX, posY,300);
		batch.setColor(1, 1, 1, 1);
		
		if(!text.isEmpty()){
			duration-= SoC.game.world.delta;
			if(duration <= 0.5f  && !dissapearing){
				dissapearing = true;
			}
			if(duration <= 0){
				text.remove();
				positions.remove();
				duration = 2f;
				dissapearing = false;
			}
		}
	}
	
	public void pop(String text, float posx, float posy){
		this.duration = 2f;
		this.timer = 0.5f;
		this.positions.add(new Vector2(posx, posy));
		this.text.add(text);
	}
}
