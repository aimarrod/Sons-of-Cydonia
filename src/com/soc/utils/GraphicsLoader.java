package com.soc.utils;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.graphics.AttackRenderer;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.utils.Constants.Characters;

public class GraphicsLoader {

	public static DirectionalAnimatedRenderer loadCharacterSpriteSheet(Texture sheet, float duration, int hsize, int vsize, boolean loops){
		int hframes = sheet.getWidth()/hsize;
		int vframes = sheet.getHeight()/vsize;
		
		DirectionalAnimatedRenderer renderer = new DirectionalAnimatedRenderer(loops);
		
		renderer.animations = new Animation[vframes];
		renderer.ox = -(hsize-Constants.Characters.WIDTH_PIXELS)*0.5f;
		renderer.oy = (vsize-Constants.Characters.HEIGHT_PIXELS)*0.5f;
		System.out.println(renderer.oy);


	   	TextureRegion[][] tmp = TextureRegion.split(sheet, hsize, vsize);
	   	for(int i = 0; i < tmp.length; i++){
	   		renderer.animations[i] = new Animation(duration/hframes, tmp[i]);
	   	}
	   	return renderer;
	}
	
	public static AttackRenderer loadDaggerThrow(){
		AttackRenderer dagger = new AttackRenderer();
		Texture sheet = new Texture(Gdx.files.internal("resources/dagger-attack.png"));
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/4, sheet.getHeight());
		dagger.animation = new Animation(0.2f/4, tmp[0]);
		return dagger;
	   	
	}

	public static AttackRenderer loadIcicle(Vector2 dir) {
		AttackRenderer icicle = new AttackRenderer();
		Texture sheet = new Texture(Gdx.files.internal("resources/magic-icicle.png"));
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/8, sheet.getHeight()/4);
		int direction = 0;
		if(dir.y == -1){
			direction = 2; 
		} else if(dir.x == 1){
			direction = 3;
		} else if(dir.x == -1){
			direction = 1;
		}
		icicle.animation = new Animation(0.2f/4, tmp[direction]);
		return icicle;
	}
	
	public static AttackRenderer loadFireball(Vector2 dir) {
		AttackRenderer icicle = new AttackRenderer();
		Texture sheet = new Texture(Gdx.files.internal("resources/magic-fireball.png"));
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/8, sheet.getHeight()/4);
		int direction = 0;
		if(dir.y == -1){
			direction = 2; 
		} else if(dir.x == 1){
			direction = 3;
		} else if(dir.x == -1){
			direction = 1;
		}
		icicle.animation = new Animation(0.2f/4, tmp[direction]);
		return icicle;
	}
}
