package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Health;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;

public class AttackCollisionSystem extends EntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Bounds> bm;
	@Mapper
	ComponentMapper<Health> hm;
	@Mapper
	ComponentMapper<Attack> am;
	@Mapper
	ComponentMapper<Velocity> vm;

	private Bag<CollisionPair> attackCollisions;

	public AttackCollisionSystem() {
		super(Aspect.getAspectForAll(Position.class, Bounds.class));
	}

	@Override
	public void initialize() {
		attackCollisions = new Bag<CollisionPair>();

		attackCollisions.add(new CollisionPair(
				Constants.Groups.PLAYER_PROJECTILES, Constants.Groups.ENEMIES, new ProjectileCollisionHandler()));
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; attackCollisions.size() > i; i++) {
			attackCollisions.get(i).checkForCollisions();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	private class CollisionPair {
		private ImmutableBag<Entity> groupEntitiesA;
		private ImmutableBag<Entity> groupEntitiesB;
		private CollisionHandler handler;

		public CollisionPair(String group1, String group2,
				CollisionHandler handler) {
			groupEntitiesA = world.getManager(GroupManager.class).getEntities(
					group1);
			groupEntitiesB = world.getManager(GroupManager.class).getEntities(
					group2);
			this.handler = handler;
		}

		public void checkForCollisions() {
			for (int a = 0; groupEntitiesA.size() > a; a++) {
				for (int b = 0; groupEntitiesB.size() > b; b++) {
					Entity entityA = groupEntitiesA.get(a);
					Entity entityB = groupEntitiesB.get(b);
					if (collisionExists(entityA, entityB)) {
						handler.handleCollision(entityA, entityB);
					}
				}
			}
		}

		private boolean collisionExists(Entity e1, Entity e2) {
			Position p1 = pm.get(e1);
			Position p2 = pm.get(e2);

			Bounds b1 = bm.get(e1);
			Bounds b2 = bm.get(e2);

			Rectangle r1 = new Rectangle(p1.x, p1.y, b1.width, b1.height);
			Rectangle r2 = new Rectangle(p2.x, p2.y, b2.width, b2.height);

			return Intersector.overlaps(r1, r2);
		}
	}

	private interface CollisionHandler {
		void handleCollision(Entity a, Entity b);
	}
	
	private class ProjectileCollisionHandler implements CollisionHandler {

		@Override
		public void handleCollision(Entity attack, Entity enemy) {
			Health health = hm.get(enemy);
			health.health -= am.get(attack).damage;
			attack.deleteFromWorld();			
		}
		
	}
}
