package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.soc.Constants;
import com.soc.algorithms.Node;
import com.soc.components.Enemy;
import com.soc.components.Position;
import com.soc.components.Velocity;

public class EnemyActuatorSystem extends EntityProcessingSystem {
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Velocity> vm;
	 @Mapper ComponentMapper<Enemy> em;
	public EnemyActuatorSystem() {
		super(Aspect.getAspectForAll(Enemy.class));
	}

	@Override
	protected void process(Entity e) {
		Enemy enemy=em.get(e);
		Position p=pm.get(e);
		Velocity v=vm.get(e);
		while(!enemy.path.isEmpty()){
			Node currentNode=enemy.path.get(0);		
			float xSubstracted=currentNode.vector.x -p.x;
			float ySubstracted=currentNode.vector.y-p.y;
			if(xSubstracted>0){
				
			}else{
				
			}
			if(ySubstracted>0){
				
			}else{
				
			}
			if(hasReached(new Vector2(p.x,p.y),currentNode.vector)){
				enemy.path.remove(0);
			}
		}
	}
	
	public static boolean hasReached(Vector2 currentPosition, Vector2 goal){
		int xCurrent=(int) currentPosition.x;
		int yCurrent=(int)currentPosition.y;
		int xGoal=(int)goal.x;
		int yGoal=(int)goal.y;
		if(xCurrent==xGoal && yCurrent==yGoal){
			return true;
		}
		return false;
		
	}

}
