package com.soc.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
	
	public static GraphicsLoader instance;
	
	public Map<String,Texture> loaded;
	
	
	public static void initialize(){
		GraphicsLoader.instance = new GraphicsLoader();
	}
	
	private GraphicsLoader(){
		loaded = new HashMap<String, Texture>();
	}
	
	public static Texture load(String texture){
		Texture tex = instance.loaded.get(texture);
		if(tex == null){
			tex = new Texture(Gdx.files.internal(Constants.Configuration.RESOURCE_DIR + texture));
			instance.loaded.put(texture, tex);
		}
		return tex;
	}


	public static void loadWarrior(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalStaticRenderer charge = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		DirectionalAnimatedRenderer run = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		AnimatedRenderer spin = new AnimatedRenderer(true);
		AnimatedRenderer fall = new AnimatedRenderer(true);
		
		attack.ox = -80;
		attack.oy = -64;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;
		run.ox = -16;
		run.oy = 0;
		charge.ox = -80;
		charge.oy = -64;
		fall.ox -= 16;
		fall.oy -= 0;
		spin.ox = -80;
		spin.oy = -64;
		
		Texture tex = load("warrior-attack.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		TextureRegion[][] tmp = TextureRegion.split(tex, 192, 192);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.35f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("warrior-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		run.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("warrior-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tex = load("warrior-charge.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		tmp = TextureRegion.split(tex, 192, 192);
		for(int i = 0; i < tmp.length; i++){
	   		charge.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("warrior-death.png"), 64, 64);
	   	death.animation = new Animation(1f/tmp[0].length, tmp[0]);
	   	character.deathTime = 1f;
		tex = load("warrior-spin.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		tmp = TextureRegion.split(tex, 192, 192);
	   	spin.animation = new Animation(0.2f/tmp[0].length, tmp[0]);
	   	tmp = TextureRegion.split(load("warrior-fall.png"), 64, 64);
	   	fall.animation = new Animation(1.2f/tmp[0].length, tmp[0]);
		
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
		character.renderers[State.CHARGING] = charge;
		character.renderers[State.SPINNING] = spin;
		character.renderers[State.FALLING] = fall;
		character.renderers[State.RUN] = run;
	}
	
	public static void loadGreenKnight(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalStaticRenderer charge = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		attack.ox = -16;
		attack.oy = -0;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;
		charge.ox = -48;
		charge.oy = -32;

		
		Texture tex = load("green-knight-attack.png");
		TextureRegion[][] tmp = TextureRegion.split(tex, 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("green-knight-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tex = load("green-knight-charge.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		tmp = TextureRegion.split(tex, 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		charge.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("green-knight-death.png"), 64, 64);
	   	death.animation = new Animation(1f/tmp[0].length, tmp[0]);
	   	character.deathTime = 1f;
		
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
		character.renderers[State.CHARGING] = charge;

	}
	
	public static void loadGoldKnight(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		AnimatedRenderer spin = new AnimatedRenderer(true);
		
		attack.ox = -48;
		attack.oy = -32;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;
		spin.ox = -48;
		spin.oy = -32;
		
		Texture tex = load("gold-knight-attack.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		TextureRegion[][] tmp = TextureRegion.split(tex, 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.35f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("gold-knight-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("gold-knight-death.png"), 64, 64);
	   	death.animation = new Animation(1f/tmp[0].length, tmp[0]);
	   	character.deathTime = 1f;
		tex = load("gold-knight-spin.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		tmp = TextureRegion.split(tex, 128, 128);
	   	spin.animation = new Animation(0.2f/tmp[0].length, tmp[0]);
		
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
		character.renderers[State.SPINNING] = spin;
	}
	
	public static void loadGoldBowKnight(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		attack.ox = -16;
		attack.oy = -0;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;
		
		Texture tex = load("gold-knight-bow.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		TextureRegion[][] tmp = TextureRegion.split(tex, 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.5f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("gold-knight-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("gold-knight-death.png"), 64, 64);
	   	death.animation = new Animation(1f/tmp[0].length, tmp[0]);
	   	character.deathTime = 1f;
		tex = load("gold-knight-spin.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		tmp = TextureRegion.split(tex, 128, 128);
		
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
	}
	

	public static void loadBlackMage(Character animations) {
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		AnimatedRenderer spin = new AnimatedRenderer(true);
		
		attack.ox = -16;
		attack.oy = -0;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;
		spin.ox = -45;
		spin.oy = -0;
		
		Texture tex = load("black-mage-attack.png");
		TextureRegion[][] tmp = TextureRegion.split(tex, 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.5f/tmp[i].length, tmp[i]);
	   	}
		
		tmp = TextureRegion.split(load("black-mage-idle.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		idle.sprites[i] = tmp[i][0];
	   	}
		
		tmp = TextureRegion.split(load("black-mage-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   	}

		tmp = TextureRegion.split(load("black-mage-death.png"), 64, 64);
	   	death.animation = new Animation(1f/tmp[0].length, tmp[0]);
	   	animations.deathTime = 1f;
	   	
		tex = load("black-mage-spinning.png");
		tmp = TextureRegion.split(tex, 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                    frames[index++] = tmp[i][j];
            }
        }
	   	spin.animation = new Animation(0.5f/frames.length, frames);
		
		animations.renderers[State.IDLE] = idle;
		animations.renderers[State.DYING] = death;
		animations.renderers[State.ATTACK] = attack;
		animations.renderers[State.WALK] = movement;
		animations.renderers[State.SPINNING] = spin;
	}
	
	public static void loadMaggot(Character character){
		DirectionalAnimatedRenderer move = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		TextureRegion[][] tmp = TextureRegion.split(load("maggot-walk.png"), 32, 32);
		for(int i = 0; i < tmp.length; i++){
	   		move.animations[i] = new Animation(0.2f/tmp[i].length, tmp[i]);
	   	}
		
		tmp = TextureRegion.split(load("blood-spill.png"), 32, 32);
		TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
		death.animation = new Animation(0.5f/deathFrames.length, deathFrames);
		character.deathTime=0.5f;
		
		character.renderers[State.WALK] = move;
		character.renderers[State.DYING] = death;
		
	}
	
	public static void loadBat(Character character){
		DirectionalAnimatedRenderer move = new DirectionalAnimatedRenderer(true);
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		TextureRegion[][] tmp = TextureRegion.split(load("bat-walk.png"), 32, 32);
		for(int i = 0; i < tmp.length; i++){
	   		move.animations[i] = new Animation(0.2f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("bat-attack.png"), 32, 32);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(1.5f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("blood-spill.png"), 32, 32);
		TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
		death.animation = new Animation(0.5f/deathFrames.length, deathFrames);
		character.deathTime=0.5f;
		
		character.renderers[State.WALK] = move;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.DYING] = death;
		
	}
	
	public static void loadSlime(Character character){
		DirectionalAnimatedRenderer move = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		TextureRegion[][] tmp = TextureRegion.split(load("slime-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		move.animations[i] = new Animation(1f/tmp[i].length, tmp[i]);
	   	}
		
		tmp = TextureRegion.split(load("slime-death.png"), 64, 64);
		TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
		death.animation = new Animation(2f/deathFrames.length, deathFrames);
		character.deathTime=2f;
		
		character.renderers[State.WALK] = move;
		character.renderers[State.DYING] = death;
		
	}
	
	public static void loadSkeleton(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		attack.ox = -16;
		attack.oy = 0;
		movement.ox -= 16;
		movement.oy -= 0;
		idle.ox -= 16;
		idle.oy -= 0;
		death.ox -= 16;
		death.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(load("skeleton-attack.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("skeleton-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(1f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("skeleton-death.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		death.animation = new Animation(0.4f/tmp[i].length, tmp[i]);
	   	}
		character.deathTime=0.4f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
	}
	
	public static void loadZombie(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		attack.ox = -16;
		attack.oy = 0;
		movement.ox -= 16;
		movement.oy -= 0;
		idle.ox -= 16;
		idle.oy -= 0;
		death.ox -= 16;
		death.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(load("zombie-attack.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(1f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("zombie-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(1f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("zombie-death.png"), 64, 64);
		TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
		death.animation = new Animation(2f/deathFrames.length, deathFrames);
		character.deathTime=2f;
		
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
		
		TextureRegion[][] tmp = TextureRegion.split(load("ballista-attack.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.4f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
        //0.4f duration of the whole animation so divided in the total of frames.
	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;

		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
	}
	
	public static void loadSatan(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement= new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		attack.ox = -0;
		attack.oy = 0;
		idle.ox -= -0;
		idle.oy -= 0;
		movement.ox -=0;
		movement.oy -= 0;
		
		TextureRegion[][] tmp = TextureRegion.split(load("satan-attack.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(1f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("satan-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(1f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
        //0.4f duration of the whole animation so divided in the total of frames.
	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.WALK] = movement;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
	}
	
	public static void loadGaiaAir(Character character){
		StaticRenderer idle = new StaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		idle.ox  = -48f;
		
		idle.sprite = new TextureRegion(load("gaia-air.png"));
		TextureRegion[][] tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }

	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
	}
	
	public static void loadGaiaDark(Character character){
		StaticRenderer idle = new StaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		idle.ox  = -48f;
		
		idle.sprite = new TextureRegion(load("gaia-dark.png"));
		TextureRegion[][] tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }

	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
	}
	
	public static void loadGaiaFlame(Character character){
		StaticRenderer idle = new StaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		idle.ox  = -48f;
		
		idle.sprite = new TextureRegion(load("gaia-flame.png"));
		TextureRegion[][] tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }

	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
	}
	
	public static void loadGaia(Character character){
		StaticRenderer idle = new StaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		idle.ox  = -48f;
		
		idle.sprite = new TextureRegion(load("gaia.png"));
		TextureRegion[][] tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }

	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
	}
	
	public static void loadGaiaAvatar(Character character){
		DirectionalAnimatedRenderer attack = new DirectionalAnimatedRenderer(false);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		DirectionalAnimatedRenderer movement = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);

		attack.ox = -16;
		attack.oy = 0;
		movement.ox = -16;
		movement.oy = 0;
		idle.ox = -16;
		idle.oy = -0;
		death.ox = -16;
		death.oy = 0;

		
		Texture tex = load("gaia-avatar-cast.png");
		tex.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		TextureRegion[][] tmp = TextureRegion.split(tex, 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		attack.animations[i]= new Animation(0.5f/tmp[i].length, tmp[i]);
	   	}
		tmp = TextureRegion.split(load("gaia-avatar-walk.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		movement.animations [i]= new Animation(0.7f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("warrior-death.png"), 64, 64);
	   	death.animation = new Animation(1f/tmp[0].length, tmp[0]);
	   	
	   	character.deathTime = 1f;
		
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
		character.renderers[State.ATTACK] = attack;
		character.renderers[State.WALK] = movement;
	}

	public static void loadMidMonster(Character character){
		DirectionalAnimatedRenderer move = new DirectionalAnimatedRenderer(true);
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		TextureRegion[][] tmp = TextureRegion.split(load("mid-monster.png"), 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		move.animations[i] = new Animation(0.5f/tmp[i].length, tmp[i]);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }

	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
	    character.renderers[State.ATTACK]=move;
	    character.renderers[State.WALK]=move;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
	}
	
	public static void loadEyeball(Character character){
		DirectionalAnimatedRenderer move = new DirectionalAnimatedRenderer(true);
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		TextureRegion[][] tmp = TextureRegion.split(load("eyeball-walk.png"), 32, 38);
		for(int i = 0; i < tmp.length; i++){
	   		move.animations[i] = new Animation(2f/tmp[i].length, tmp[i]);
	   	}
		
		tmp = TextureRegion.split(load("eyeball-death.png"), 32, 38);
		TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }
		death.animation = new Animation(1f/deathFrames.length, deathFrames);
		character.deathTime=0.8f;
		
		character.renderers[State.WALK] = move;
		character.renderers[State.DYING] = death;
		
	}
	
	public static void loadFireStoneMonster(Character character){
		DirectionalStaticRenderer idle = new DirectionalStaticRenderer();
		AnimatedRenderer death = new AnimatedRenderer(false);
		
		character.renderers = new Renderer[State.STATENUM];
		
		TextureRegion [][]tmp = TextureRegion.split(load("fire-stone-monster.png"), 128, 128);
		for(int i = 0; i < tmp.length; i++){
			System.out.println(i);
	   		idle.sprites[i] = tmp[i][0];
	   	}
		
		tmp = TextureRegion.split(load("ballista-death.png"), 128, 64);
        TextureRegion [] deathFrames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        deathFrames[index++] = tmp[i][j];
                }
        }

	    death.animation = new Animation(1f/deathFrames.length, deathFrames);
	    character.deathTime=1f;
		character.renderers[State.IDLE] = idle;
		character.renderers[State.DYING] = death;
	}
	
	public static AnimatedRenderer loadDaggerThrow(){
		AnimatedRenderer dagger = new AnimatedRenderer(true);
		TextureRegion[][] tmp = TextureRegion.split(load("dagger-attack.png"), 64, 64);
		dagger.animation = new Animation(0.2f/4, tmp[0]);
		return dagger;
	   	
	}

	public static DirectionalStaticRenderer loadArrow(){
		DirectionalStaticRenderer renderer=new DirectionalStaticRenderer();
		TextureRegion[][] tmp = TextureRegion.split(load("hunter-arrow.png"), 64, 64);
		for(int i = 0; i < tmp.length; i++){
	   		renderer.sprites[i] = tmp[i][0];
	   	}
		renderer.ox=-32;
		renderer.oy=-32;
		return renderer;
	   	
	}
	public static AnimatedRenderer loadIcicle(Vector2 dir) {
		AnimatedRenderer icicle = new AnimatedRenderer(true);
		TextureRegion[][] tmp = TextureRegion.split(load("magic-icicle.png"), 64, 64);
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
		TextureRegion[][] tmp = TextureRegion.split(load("magic-fireball.png"), 64, 64);
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
	
	public static AnimatedRenderer loadCloud(){
		AnimatedRenderer cloud = new AnimatedRenderer(true);
		TextureRegion[][] tmp = TextureRegion.split(load("cloud.png"), 256, 256);
        TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        cloud.animation = new Animation(2f/frames.length, frames);
        cloud.ox = -128;
        cloud.oy = -128;
        return cloud;
	}
	
	public static AnimatedRenderer loadQuake(){
		AnimatedRenderer quake = new AnimatedRenderer(true);
		TextureRegion[][] tmp = TextureRegion.split(load("quake.png"), 256, 128);
        quake.animation = new Animation(Constants.Spells.QUAKEBLADE_TICK_INTERVAL, tmp[0]);
        return quake;
	}
	
	public static AnimatedRenderer loadTornado(){
		AnimatedRenderer tornado = new AnimatedRenderer(true);
		
		tornado.ox = -10;
		TextureRegion[][] tmp = TextureRegion.split(load("tornado.png"), 64, 64);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        tornado.animation = new Animation(0.05f, frames);
        return tornado;
	}
	
	public static AnimatedRenderer loadLifeShield(){
		AnimatedRenderer lifeShield = new AnimatedRenderer(true);
		lifeShield.ox=-45;
		lifeShield.oy=-35;
		TextureRegion[][] tmp = TextureRegion.split(load("life-shield.png"), 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        lifeShield.animation = new Animation(0.05f, frames);
        return lifeShield;
	}
	
	public static AnimatedRenderer loadFlame(){
		AnimatedRenderer flame = new AnimatedRenderer(true);
		flame.ox=0;
		flame.oy=0;
		TextureRegion[][] tmp = TextureRegion.split(load("flame.png"), 64, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        flame.animation = new Animation(4f/17, frames);
        return flame;
	}
	
	public static AnimatedRenderer loadFlameWall(){
		AnimatedRenderer flame = new AnimatedRenderer(true);
		flame.ox=0;
		flame.oy=0;
		TextureRegion[][] tmp = TextureRegion.split(load("flame-wall.png"), 64, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        flame.animation = new Animation(4f/9, frames);
        return flame;
	}
	
	public static AnimatedRenderer loadRageAura(){
		AnimatedRenderer rageAura = new AnimatedRenderer(true);
		rageAura.ox=-44;
		rageAura.oy=-20;
		TextureRegion[][] tmp = TextureRegion.split(load("rage-aura.png"), 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        rageAura.animation = new Animation(0.05f, frames);
        return rageAura;
	}
	
	public static AnimatedRenderer loadTeleport(){
		AnimatedRenderer teleport = new AnimatedRenderer(false);
		teleport.ox= -80;
		teleport.oy= -65;
		TextureRegion[][] tmp = TextureRegion.split(load("teleport.png"), 192, 192);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        teleport.animation = new Animation(Constants.Buff.TELEPORT_CAST_TIME/frames.length, frames);
        teleport.animation.setPlayMode(Animation.REVERSED);
        return teleport;
	}
	
	public static DirectionalAnimatedRenderer loadShield(){
		DirectionalAnimatedRenderer shield = new DirectionalAnimatedRenderer(false);
		shield.ox= -50;
		shield.oy= -40;
		TextureRegion[][] tmp = TextureRegion.split(load("shield.png"), 128, 128);
		for(int i = 0; i < tmp.length; i++){
	   		shield.animations[i] = new Animation(Constants.Buff.SHIELD_CHARGE_TIME/tmp[i].length, tmp[i]);
	   	}

        return shield;
	}
	
	public static AnimatedRenderer loadMeteorFall(){
		AnimatedRenderer meteor = new AnimatedRenderer(true);
		meteor.ox = -16;
		TextureRegion[][] tmp = TextureRegion.split(load("meteor-falling.png"), 32, 32);
        meteor.animation = new Animation(0.05f, tmp[0]);
        return meteor;
	}
	
	public static AnimatedRenderer loadWindblade(){
		AnimatedRenderer windb = new AnimatedRenderer(true);
		TextureRegion[][] tmp = TextureRegion.split(load("windblade.png"), 48, 48);
        windb.animation = new Animation(0.05f, tmp[0]);
        return windb;
	}
	
	public static AnimatedRenderer loadMeteorBlast(){
		AnimatedRenderer meteor = new AnimatedRenderer(true);
		meteor.ox = -128;
		meteor.oy = -64;
		TextureRegion[][] tmp = TextureRegion.split(load("meteor-blast.png"), 256, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        meteor.animation = new Animation(Constants.Spells.METEOR_BLAST_TICK_INTERVAL, frames);
        return meteor;
	}

	public static AnimatedRenderer loadFireStoneInitial(){
		AnimatedRenderer fireStone = new AnimatedRenderer(false);
		fireStone.ox+=0;
		fireStone.oy+=10;
		TextureRegion[][] tmp = TextureRegion.split(load("fire-stone-initial.png"), 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
       	fireStone.animation = new Animation(0.25f, frames);
        return fireStone;
	}
	public static AnimatedRenderer loadFireStoneRunning(){
		AnimatedRenderer fireStone = new AnimatedRenderer(true);
		fireStone.ox-=39;
		fireStone.oy-=37;
		TextureRegion[][] tmp = TextureRegion.split(load("fire-stone-running.png"), 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
       	fireStone.animation = new Animation(0.05f, frames);
        return fireStone;
	}
	
	public static AnimatedRenderer loadFireStoneDeath(){
		
		AnimatedRenderer fireStone = new AnimatedRenderer(false);
		fireStone.ox+=0;
		fireStone.oy+=50;
		TextureRegion[][] tmp = TextureRegion.split(load("fire-stone-death.png"), 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
       	fireStone.animation = new Animation(0.25f, frames);
        return fireStone;
	}
	
	public static StaticRenderer loadMeteorShadow(){
		StaticRenderer meteor = new StaticRenderer();
		meteor.ox = -16;
		meteor.oy = -16;
		meteor.sprite = new TextureRegion(load("meteor-shadow.png"));
        return meteor;
	}
	
	public static AnimatedRenderer loadTentacles(){
		AnimatedRenderer meteor = new AnimatedRenderer(true);
		meteor.ox = -230;
		meteor.oy = -120;
		TextureRegion[][] tmp = TextureRegion.split(load("tentacles.png"), 512, 512);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        meteor.animation = new Animation(Constants.Spells.TENTACLES_DURATION/frames.length, frames);
        return meteor;
	}

	public static AnimatedRenderer loadAntiVenomFountain() {
		AnimatedRenderer fountain = new AnimatedRenderer(true);
		fountain.ox-=50;
		fountain.oy-=20;
		TextureRegion[][] tmp = TextureRegion.split(load("removePoison.png"), 192, 192);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
          }  }    
        fountain.animation = new Animation(0.05f, frames);
        return fountain;
    }
	
	public static AnimatedRenderer loadAirCircle(){
		AnimatedRenderer circle = new AnimatedRenderer(true);
		circle.ox = -25;
		circle.oy = -25;
		TextureRegion[][] tmp = TextureRegion.split(load("air-circle.png"), 50, 50);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        circle.animation = new Animation(Constants.Spells.AIR_CIRCLE_TIME/frames.length, frames);
        circle.animation.setPlayMode(Animation.LOOP_REVERSED);
        return circle;
	}
	
	public static AnimatedRenderer loadWall(){
		AnimatedRenderer wall = new AnimatedRenderer(true);
		wall.ox = -16;
		wall.oy = -16;
		TextureRegion[][] tmp = TextureRegion.split(load("wall.png"), 64, 64);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }

        wall.animation = new Animation(1f/frames.length, frames);
        return wall;
	}
	
	public static AnimatedRenderer loadRedPush(){
		AnimatedRenderer flame = new AnimatedRenderer(false);
		flame.ox=0;
		flame.oy-=20;
		TextureRegion[][] tmp = TextureRegion.split(load("satan-push2.png"), 128, 128);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        flame.animation = new Animation(0.05f, frames);
        return flame;
	}
	
	public static AnimatedRenderer loadRedCast(){
		AnimatedRenderer flame = new AnimatedRenderer(true);
		flame.ox=0;
		flame.oy-=20;
		TextureRegion[][] tmp = TextureRegion.split(load("castRed.png"), 64,64);
		TextureRegion [] frames = new TextureRegion[tmp.length * tmp[0].length];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < tmp[0].length; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        flame.animation = new Animation(0.05f, frames);
        return flame;
	}

	public static AnimatedRenderer loadBoneThrow() {
		AnimatedRenderer bone = new AnimatedRenderer(true);
		TextureRegion[][] tmp = TextureRegion.split(load("bone-projectile.png"), 32, 32);
        bone.animation = new Animation(0.03f, tmp[0]);
        return bone;
	}

}
