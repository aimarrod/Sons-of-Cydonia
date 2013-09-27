package com.soc.game.systems;



import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.PlayerManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.soc.algorithms.AStar;
import com.soc.algorithms.Node;
import com.soc.game.components.Enemy;
import com.soc.game.components.Health;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;


public class EnemyActuatorSystem extends EntityProcessingSystem {
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Velocity> vm;
	 @Mapper ComponentMapper<Enemy> em;
	 @Mapper ComponentMapper<State> sm;
	 @Mapper ComponentMapper<Health> hm;

	public EnemyActuatorSystem() {
		super(Aspect.getAspectForAll(Enemy.class));
	}

	@Override
	protected void process(Entity e) {
		Enemy enemy=em.get(e);
		Position p=pm.get(e);
		Velocity v=vm.get(e);
		State s = sm.get(e);
		Health h = hm.get(e);
		Entity player=world.getManager(PlayerManager.class).getEntitiesOfPlayer(Constants.Groups.PLAYER).get(0);
		Position playp = pm.get(player);
		
		float dstx = 0f;
		float dsty = 0f;
		
		if(h.health<=0){
			e.deleteFromWorld();
			return;
		}
		
		if(Math.hypot(playp.x-p.x, playp.y-p.y)>enemy.vision){
			v.vx = 0;
			v.vy = 0;
		} else {
			if(AStar.instance.isDirectPath((int)p.x, (int)p.y, (int)playp.x, (int)playp.y)){
				enemy.path.clear();
				dstx=playp.x-p.x;
				dsty=playp.y-p.y;
			} else if(enemy.path.isEmpty()){
				enemy.path = AStar.instance.getPath(new Vector2(p.x, p.y), new Vector2(playp.x, playp.y));
			}

		
		
			if(!enemy.path.isEmpty()){	
				Node currentNode=enemy.path.get(0);		
				dstx=currentNode.x-p.x;
				dsty=currentNode.y-p.y;
				if(hasReached(p.x,p.y,currentNode.x, currentNode.y)){
					enemy.path.remove(0);
				}
			}
		}
		
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
	}
	
	public static boolean hasReached(float origx, float origy, float destx, float desty){
		return (Math.abs(origx-destx)<=16 && Math.abs(origy-desty)<=16);
	}

}
