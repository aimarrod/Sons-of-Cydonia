package com.soc.utils;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.hud.HudSystem;

public class DialogBox {
	public Queue<String> text;
	public Queue<Position> positions;
	float timer;
	private Skin skin;
	private Texture chatBaloon;
	private BitmapFont font;
	private float duration;
	private boolean dissapearing;
	private Color fontColor;
	private float lowerBound, upperBound, leftBound, rightBound, posX, posY, dialength, dialheight;
	
	public DialogBox(){
		this.text = new LinkedList<String>();
		this.positions = new LinkedList<Position>();
		this.timer = 0.5f;
		this.chatBaloon = GraphicsLoader.load("chat-baloon.png");
		this.skin = SoC.game.skin;
		this.font =skin.getFont("gameFont");
		this.fontColor = skin.getColor("yellow");
		this.duration = 2f;
		
	}
	
	public void draw (SpriteBatch batch) {
		if(text.isEmpty()) return;
		batch.setColor(1, 1, 1, timer);
		font.setColor(fontColor.r, fontColor.g, fontColor.b, timer);
		font.setScale(0.5f);
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
		
		
		posX = positions.peek().x;
		posY = positions.peek().y;
		
		String current = text.peek();
		dialength = (current.length()*font.getSpaceWidth());
		int lines = (int) Math.ceil(dialength/300);
		if(dialength > 300){
			dialength = 300;
		}
		dialheight = lines* (font.getLineHeight() + 15);
		dialheight += dialheight*0.5;
		
		if(posX < leftBound){
			posX = leftBound + 50;
		} else if(posX + dialength > rightBound){
			posX = rightBound - dialength;
		}
		
		if(posY < lowerBound + dialheight){
			posY = lowerBound + 20;
		} else if(posY > upperBound){
			posY = upperBound - dialheight;
		}
		
		
		batch.draw(chatBaloon, posX, posY, dialength + 50, dialheight);
		font.drawWrapped(batch, text.peek(), posX+ 25, posY + dialheight-10, 280);
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
	
	public void pop(String text, Position pos){
		this.positions.add(pos);
		this.text.add(text);
	}
}
