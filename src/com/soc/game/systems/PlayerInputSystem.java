
package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Character;
import com.soc.game.components.Delay;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.hud.HudSystem;


	public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor{
		 @Mapper ComponentMapper<Velocity> vm;
		 @Mapper ComponentMapper<State> sm;
		 @Mapper ComponentMapper<Position>pm;
		 @Mapper ComponentMapper<Player> plm;
		 @Mapper ComponentMapper<Stats> stm;
		 @Mapper ComponentMapper<Character> cm;
		 
		 @SuppressWarnings("unchecked")
		 public PlayerInputSystem() {
			 super(Aspect.getAspectForAll(Velocity.class, Player.class, State.class,Position.class));
		 }
		  
		 @Override
		 protected void initialize() {
			 SoC.game.inputMultiplexer.addProcessor(this);
		 }
		 
		 @Override
		 protected void process(Entity e) {
		   
			 Velocity vel = vm.get(e);			 
			 State state = sm.get(e);
			 Position pos=pm.get(e);
			 Player player = plm.get(e);
			 Stats st = stm.get(e);
			 
			  
			 if(state.state < State.BLOCKED){
				if(Gdx.input.isKeyPressed(player.attack)){
					if(state.state != State.ATTACK){
						st.mana--;
						state.state = State.ATTACK;
						e.addComponent(new Delay(Constants.Groups.PLAYER_ATTACKS, SoC.game.spells[st.attack].cast, 0.4f, st.attack));
						e.changedInWorld();
						vel.vx = 0;
						vel.vy = 0;
					}
					return;
				}
				
				 
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
			 
			 	if(moving){
				 	state.state = State.WALK;
			 	} else {
				 	state.state = State.IDLE;
			 	}
			 }
			 			 
		 }

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			if(Gdx.input.isKeyPressed(plm.get(SoC.game.player).inventory)){
				world.getSystem(HudSystem.class).toggleInventory();
				return true;
			}
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
