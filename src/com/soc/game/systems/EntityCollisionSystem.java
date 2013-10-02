package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;
import com.soc.utils.Constants.Groups;

public class EntityCollisionSystem extends VoidEntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Bounds> bm;
	@Mapper
	ComponentMapper<Stats> sm;
	@Mapper
	ComponentMapper<Attack> am;
	@Mapper
	ComponentMapper<Velocity> vm;

	private Bag<CollisionGroup> collisionGroups;

	public EntityCollisionSystem() {
		super();
	}

	@Override
	public void initialize() {
		collisionGroups = new Bag<CollisionGroup>();
		collisionGroups.add(new AttackCollision(Constants.Groups.PLAYER_ATTACKS, Constants.Groups.ENEMIES));
		collisionGroups.add(new AttackCollision(Constants.Groups.ENEMY_ATTACKS, Constants.Groups.PLAYERS));
	}

	

	@Override
	protected void processSystem() {
		for(int i = 0; i < collisionGroups.size(); i++){
			collisionGroups.get(i).processCollisions();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	private interface CollisionGroup{
		public void processCollisions();
	}
	
	private class AttackCollision implements CollisionGroup{
		private ImmutableBag<Entity> attacks;
		private ImmutableBag<Entity> receivers;

		public AttackCollision(String attackGroup, String receiverGroup) {
			attacks = world.getManager(GroupManager.class).getEntities(attackGroup);
			receivers = world.getManager(GroupManager.class).getEntities(receiverGroup);
		}
		
		@Override
		public void processCollisions(){
			for (int a = 0; attacks.size() > a; a++) {
				
				Entity atk = attacks.get(a);
				Attack attack = am.get(atk);
				Bounds b1 = bm.get(atk);
				Position p1 = pm.get(atk);
				
				for (int b = 0; receivers.size() > b; b++) {
					
					Entity enemy = receivers.get(b);
					if(attack.processor.collision(enemy, p1, b1, pm.get(enemy), bm.get(enemy))){
						attack.processor.handle(enemy, attack, sm.get(enemy));
					}
				}
				
			}

		}
	}

}
