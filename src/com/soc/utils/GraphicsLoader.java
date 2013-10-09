package com.soc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.game.components.Character;
import com.soc.game.components.State;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.game.graphics.DirectionalStaticRenderer;
import com.soc.game.graphics.Renderer;
import com.soc.game.graphics.StaticRenderer;

public class GraphicsLoader {

	public static void loadWarrior(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalStaticRenderer charge = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		attack.ox = -48;
		attack.oy = -32;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;
		movement.ox = -16;
		movement.oy = 0;
		charge.ox -= 16;
		charge.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/warrior-attack.png")), 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/warrior-walk.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/warrior-charge.png")), 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		charge.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/warrior-death.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		death.animation = new Animation(1f/tmp[i].length, tmp[i]);
	   	}
		
		character.renderers = new Renderer[State.STATENUM];
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
		character.renderers[State.CHARGING] = charge;
	}
	
	public static void loadSkeleton(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		StaticRenderer death = new StaticRenderer();
		
		character.renderers = new Renderer[State.STATENUM];
		attack.ox = -16;
		attack.oy = 0;
		movement.ox -= 16;
		movement.oy -= 0;
		idle.ox -= 16;
		idle.oy -= 0;
		death.ox -= 16;
		death.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/skeleton-attack.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(1f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/skeleton-death.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		death.sprite = tmp[i][0];
	   	}
		character.renderers = new Renderer[State.STATENUM];
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
	}
	
	public static void loadBallista(Character character){
		//False repeat at the end
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		attack.ox = -0;
		attack.oy = 0;
		idle.ox -= -0;
		idle.oy -= 0;
		death.ox -=20;
		death.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/ballista-attack.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/ballista-death.png")), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
        //0.4f duration of the whole animation so divided in the total of frames.
	   death.animation = new Animation(0.4f/deathFrames.length, deathFrames);

		character.renderers = new Renderer[State.STATENUM];
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
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
	
	public static AnimatedRenderer loadDaggerThrow(){
		AnimatedRenderer dagger = new AnimatedRenderer(true);
		Texture sheet = new Texture(Gdx.files.internal("resources/dagger-attack.png"));
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/4, sheet.getHeight());
		dagger.animation = new Animation(0.2f/4, tmp[0]);
		return dagger;
	   	
	}

	public static DirectionalStaticRenderer loadArrow(){
		DirectionalStaticRenderer renderer=new DirectionalStaticRenderer();
		TextureRegion[][] tmp = TextureRegion.split(new Texture(Gdx.files.internal("resources/hunter-arrow.png")), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		renderer.sprites[i] = tmp[i][0];
	   	}
		renderer.ox=-32;
		renderer.oy=-32;
		return renderer;
	   	
	}
	public static AnimatedRenderer loadIcicle(Vector2 dir) {
		AnimatedRenderer icicle = new AnimatedRenderer(true);
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
	
	public static AnimatedRenderer loadFireball(Vector2 dir) {
		AnimatedRenderer icicle = new AnimatedRenderer(true);
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
