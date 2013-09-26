package com.soc.game.systems;



import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.soc.algorithms.Node;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;


public class EnemyActuatorSystem extends EntityProcessingSystem {
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Velocity> vm;
	 @Mapper ComponentMapper<Enemy> em;
	 @Mapper ComponentMapper<State> sm;

	public EnemyActuatorSystem() {
		super(Aspect.getAspectForAll(Enemy.class));
	}

	@Override
	protected void process(Entity e) {
		Enemy enemy=em.get(e);
		Position p=pm.get(e);
		Velocity v=vm.get(e);
		State s = sm.get(e);
		
		
		if(!enemy.path.isEmpty()){	
			Node currentNode=enemy.path.get(0);		
			float dstx=currentNode.vector.x-p.x;
			float dsty=currentNode.vector.y-p.y;
			
				if(Math.abs(dstx)<16){
					v.vx=0;
				} else { 
					if(dstx>0){
						v.vx=v.speed;
						s.direction = State.EAST;
					}else{
						v.vx=-v.speed;
						s.direction = State.WEST;
					}
				}
				if(Math.abs(dsty)<16){
					v.vy=0;
				} else {
					if(dsty>0){
						v.vy=v.speed;
						s.direction = State.NORTH;
					}else {
						v.vy=-v.speed;
						s.direction = State.SOUTH;
					}
				}
				
				
				

				if(hasReached(new Vector2(p.x,p.y),currentNode.vector)){
					enemy.path.remove(0);
				}
		} else {
			v.vx =0;
			v.vy=0;
		}
	}
	
	public static boolean hasReached(Vector2 currentPosition, Vector2 goal){
		return (Math.abs(currentPosition.x-goal.x)<=16 && Math.abs(currentPosition.y-goal.y)<=16);
	}

}
