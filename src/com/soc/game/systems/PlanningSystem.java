package com.soc.game.systems;


import java.util.ArrayList;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.PlayerManager;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.soc.algorithms.AStar;
import com.soc.algorithms.Node;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;


public class PlanningSystem extends IntervalEntityProcessingSystem {
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Velocity> vm;
	public PlanningSystem() {
		super(Aspect.getAspectForAll(Enemy.class), 0.5f);
	}

	@Override
	protected void process(Entity e) {
		Entity player=world.getManager(PlayerManager.class).getEntitiesOfPlayer(Constants.Groups.PLAYER).get(0);
		Position position=pm.get(e);  
		Position positionPlayer=player.getComponent(Position.class);
		ArrayList<Node> path=AStar.getPath(new Vector2(position.x,position.y), new Vector2(positionPlayer.x,positionPlayer.y));
		e.getComponent(Enemy.class).path=path;
		
	}

}
