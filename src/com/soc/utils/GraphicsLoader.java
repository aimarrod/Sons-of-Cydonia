package com.soc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.components.Character;
import com.soc.game.graphics.AttackRenderer;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.game.graphics.DirectionalStaticRenderer;
import com.soc.utils.Constants.Characters;

public class GraphicsLoader {

	public static void loadWarrior(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		attack.ox = -48;
		attack.oy = -32;
		movement.ox -= 16;
		movement.oy -= 0;
		idle.ox -= 16;
		idle.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/warrior-attack.png")), 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/warrior-walk.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(1f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		character.attack = attack;
		character.idle = idle;
		character.movement = movement;		
	}
	
	public static DirectionalAnimatedRenderer loadCharacterSpriteSheet(Texture sheet, float duration, int hsize, int vsize, boolean loops){
		int hframes = sheet.getWidth()/hsize;
		int vframes = sheet.getHeight()/vsize;
		
		DirectionalAnimatedRenderer renderer = new DirectionalAnimatedRenderer(loops);
		
		renderer.animations = new Animation[vframes];
		renderer.ox = -(hsize-Constants.Characters.FEET_WIDTH)*0.5f;
		renderer.oy = -Constants.Characters.FEET_HEIGTH*0.5f;


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
