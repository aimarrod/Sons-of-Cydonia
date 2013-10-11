
package com.soc.game.systems;

import java.io.IOException;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Character;
import com.soc.game.components.Delay;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.spells.Spell;
import com.soc.hud.HudSystem;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.FloatingText;
import com.soc.utils.GameLoader;


	public class PlayerInputSystem extends VoidEntitySystem implements InputProcessor{
		 @Mapper ComponentMapper<Velocity> vm;
		 @Mapper ComponentMapper<State> sm;
		 @Mapper ComponentMapper<Position>pm;
		 @Mapper ComponentMapper<Player> plm;
		 @Mapper ComponentMapper<Stats> stm;
		 @Mapper ComponentMapper<Character> cm;
		 
		 public PlayerInputSystem() {
			 super();
		 }
		  
		 @Override
		 protected void initialize() {
			 //SoC.game.inputMultiplexer.addProcessor(this);
		 }
		 
		 @Override
		 protected void processSystem() {
		   
			 Velocity vel = vm.get(SoC.game.player);			 
			 State state = sm.get(SoC.game.player);
			 Position pos=pm.get(SoC.game.player);
			 Player player = plm.get(SoC.game.player);
			 Stats st = stm.get(SoC.game.player);
			  
			 if(state.state < State.BLOCKED || state.state == State.SPINNING){
				 
				boolean moving = false;
				 
			 	if(Gdx.input.isKeyPressed(player.move_up)){
				 	vel.vy = vel.speed;
				 	moving = true;
			 	} else if(Gdx.input.isKeyPressed(player.move_down)){
				 	vel.vy = -vel.speed;
				 	moving = true;
			 	} else {
				 	vel.vy = 0;
			 	}
			 
			 	if(Gdx.input.isKeyPressed(player.move_left)){
				 	vel.vx = -vel.speed;
				 	moving = true;
			 	} else if(Gdx.input.isKeyPressed(player.move_right)){
				 	vel.vx = vel.speed;
				 	moving = true;
			 	} else {
				 	vel.vx = 0;
			 	}
			 	
			 	if(moving){
			 		pos.direction.x = Math.signum(vel.vx);
					pos.direction.y = Math.signum(vel.vy);
			 	}
			 
			 	if(state.state == State.SPINNING) return;
			 	if(moving){
				 	state.state = State.WALK;
			 	} else {
				 	state.state = State.IDLE;
			 	}
			 }
			 			 
		 }

		@Override
		public boolean keyDown(int keycode) {
			if(keycode==plm.get(SoC.game.player).inventory){
				world.getSystem(HudSystem.class).toggleInventory();
				return true;
			}
			if(keycode==plm.get(SoC.game.player).characterMenu){
				world.getSystem(HudSystem.class).toogleCharacterMenu();
				return true;
			}
			if(keycode==plm.get(SoC.game.player).gameMenu){
				world.getSystem(HudSystem.class).toogleGameMenu();
				return true;
			}
			
			
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			
			Entity player = SoC.game.player;
			Position pos = pm.get(player);
			State state = sm.get(player);
			Stats stats = stm.get(player);
			Player controls = plm.get(player);
			Velocity vel = vm.get(player);
			
			if(state.state >= State.BLOCKED) return false;
			
			if(keycode == controls.attack){
				int spellnum = stm.get(player).attack;
				Spell spell = SoC.game.spells[spellnum];
				state.state = spell.state;
				player.addComponent(new Delay(Constants.Groups.PLAYER_ATTACKS, spell.cast, spell.blocking, spellnum));
				player.changedInWorld();
				vel.vx = 0;
				vel.vy = 0;
				return true;
			}
			
			for(int i = 0; i < controls.spellkeys.length; i++){
				if(keycode == controls.spellkeys[i]){
					int spellnum = stm.get(player).spells[i];
					Spell spell = SoC.game.spells[spellnum];
					if(stats.mana < spell.mana){
						SoC.game.renderSystem.texts.add(new FloatingText("No Mana!", Constants.Configuration.LABEL_DURATION, pos.x, pos.y, Constants.Configuration.LABEL_SPEED));
						return true;
					}
					stats.mana -= spell.mana;
					state.state = spell.state;
					player.addComponent(new Delay(Constants.Groups.PLAYER_ATTACKS, spell.cast, spell.blocking, spellnum));
					player.changedInWorld();
					vel.vx = 0;
					vel.vy = 0;
					return true;
				}
			}
			
			return false;
		}

		@Override
		public boolean keyTyped(char character) {

			
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
	}
