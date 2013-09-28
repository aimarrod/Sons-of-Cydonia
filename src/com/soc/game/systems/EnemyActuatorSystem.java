package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.soc.algorithms.AStar;
import com.soc.algorithms.Node;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants;

public class EnemyActuatorSystem extends EntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Velocity> vm;
	@Mapper
	ComponentMapper<Enemy> em;
	@Mapper
	ComponentMapper<State> sm;
	@Mapper
	ComponentMapper<Stats> stm;

	public Bag<Actuator> actuators;

	@SuppressWarnings("unchecked")
	public EnemyActuatorSystem() {
		super(Aspect.getAspectForAll(Enemy.class));
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void initialize() {
		actuators = new Bag<Actuator>();
		actuators.add(new SkeletonActuator());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; actuators.size() > i; i++) {
			actuators.get(i).process();
		}
	}

	private class SkeletonActuator implements Actuator {
		ImmutableBag<Entity> entities;

		public SkeletonActuator() {
			entities = world.getManager(GroupManager.class).getEntities(
					Constants.Groups.SKELETONS);
		}

		@Override
		public void process() {

			for (int i = 0; i < entities.size(); i++) {

				Entity e = entities.get(i);
				Enemy enemy = em.get(e);
				Position p = pm.get(e);
				Velocity v = vm.get(e);
				State state = sm.get(e);
				Stats stats = stm.get(e);
				Entity player = world.getManager(PlayerManager.class)
						.getEntitiesOfPlayer(Constants.Groups.PLAYER).get(0);
				Position playp = pm.get(player);

				float dstx = 0f;
				float dsty = 0f;

				if (stats.health <= 0) {
					e.deleteFromWorld();
					return;
				}

				if (Math.hypot(playp.x - p.x, playp.y - p.y) > enemy.vision) {
					v.vx = 0;
					v.vy = 0;
				} else {
					if (AStar.instance.isDirectPath((int) p.x, (int) p.y,
							(int) playp.x, (int) playp.y)) {
						enemy.path.clear();
						dstx = playp.x - p.x;
						dsty = playp.y - p.y;
					} else if (enemy.path.isEmpty()) {
						enemy.path = AStar.instance.getPath(new Vector2(p.x,
								p.y), new Vector2(playp.x, playp.y));
					}

					if (!enemy.path.isEmpty()) {
						Node currentNode = enemy.path.get(0);
						dstx = Math.abs(currentNode.x - p.x);
						dsty = Math.abs(currentNode.y - p.y);
						if (dstx <= 16 && dsty <= 16) {
							enemy.path.remove(0);
						}
					}
				}

				if (Math.abs(dstx) < 16) {
					v.vx = 0;
				} else {
					if (dstx > 0) {
						v.vx = v.speed;
						state.direction = State.EAST;
					} else {
						v.vx = -v.speed;
						state.direction = State.WEST;
					}
				}
				if (Math.abs(dsty) < 16) {
					v.vy = 0;
				} else {
					if (dsty > 0) {
						v.vy = v.speed;
						state.direction = State.NORTH;
					} else {
						v.vy = -v.speed;
						state.direction = State.SOUTH;
					}
				}
			}
		}
	}

	private interface Actuator {
		public void process();
	}

}
