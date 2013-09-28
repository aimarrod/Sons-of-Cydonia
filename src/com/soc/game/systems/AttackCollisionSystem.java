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
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;
import com.soc.utils.Constants.Groups;

public class AttackCollisionSystem extends EntitySystem {
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

	private Bag<Collisioner> attackCollisions;

	public AttackCollisionSystem() {
		super(Aspect.getAspectForAll(Position.class, Bounds.class));
	}

	@Override
	public void initialize() {
		attackCollisions = new Bag<Collisioner>();

		attackCollisions.add(new ProjectileCollisions());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; attackCollisions.size() > i; i++) {
			attackCollisions.get(i).process();
			attackCollisions.get(i).checkForCollisions();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	private interface Collisioner{
		public void process();
		public void checkForCollisions();
	}
	
	private class ProjectileCollisions implements Collisioner{
		private ImmutableBag<Entity> projectiles;
		private ImmutableBag<Entity> enemies;

		public ProjectileCollisions() {
			projectiles = world.getManager(GroupManager.class).getEntities(
					Groups.PLAYER_PROJECTILES);
			enemies = world.getManager(GroupManager.class).getEntities(
					Groups.ENEMIES);
		}

		@Override
		public void process() {
			for (int a = 0; projectiles.size() > a; a++) {
				Entity e = projectiles.get(a);
				Position p = pm.get(e);
				Bounds b = bm.get(e);
				Velocity v = vm.get(e);
				
				am.get(e).processor.process(e, p, b, v, world.delta);
			}
		}
		
		@Override
		public void checkForCollisions(){
			for (int a = 0; projectiles.size() > a; a++) {
				Entity proj = projectiles.get(a);
				Attack attack = am.get(proj);
				Bounds b1 = bm.get(proj);
				Position p1 = pm.get(proj);
				
				for (int b = 0; enemies.size() > b; b++) {
					Entity enemy = enemies.get(b);
					if(attack.processor.collision(enemy, p1, b1, pm.get(enemy), bm.get(enemy))){
						attack.processor.handle(enemy, attack, sm.get(enemy));
					}
				}
			}

		}
	}
}
