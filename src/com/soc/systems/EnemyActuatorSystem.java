package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.soc.algorithms.AStar;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;


public class EnemyActuatorSystem extends EntityProcessingSystem {
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Velocity> vm;
	public EnemyActuatorSystem() {
		super(Aspect.getAspectForAll(Enemy.class));
	}

	@Override
	protected void process(Entity e) {
		  Position position = pm.get(e);
		  Velocity velocity = vm.get(e);
		  Entity player=world.getManager(PlayerManager.class).getEntitiesOfPlayer(Constants.Groups.PLAYER).get(0);
		  Position positionPlayer=player.getComponent(Position.class);
		  //AStar.calculateAStar(new Vector2(position.x,position.y), new Vector2(positionPlayer.x,positionPlayer.y));
		  
		  position.x += velocity.vx*world.delta;
		  position.y += velocity.vy*world.delta;		
	}

}
