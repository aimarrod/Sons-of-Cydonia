package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.Timer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.soc.algorithms.AStar;
import com.soc.algorithms.Node;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Expires;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;

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
				Entity player = world.getManager(TagManager.class).getEntity(Constants.Tags.PLAYER);
				Position playp = pm.get(player);

				float dstx = 0f;
				float dsty = 0f;
				boolean dstModified=false;
				if(state.state >= State.BLOCKED){
					continue;
				}
				

				if (Math.hypot(playp.x - p.x, playp.y - p.y) > enemy.vision) {
					v.vx = 0;
					v.vy = 0;
					state.state=State.IDLE;
				} else {
					dstModified=true;
					if (AStar.instance.isDirectPath(p, playp)) {
						enemy.path.clear();
						dstx = playp.x - p.x;
						dsty = playp.y - p.y;
					} else if (enemy.path.isEmpty()) {
						enemy.path = AStar.instance.getPath(p, playp);
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
					} else {
						v.vx = -v.speed;
					}
				}
				if (Math.abs(dsty) < 16) {
					v.vy = 0;
				} else {
					if (dsty > 0) {
						v.vy = v.speed;
					} else {
						v.vy = -v.speed;
					}
				}
				if(dstModified && (Math.abs(dstx) < 16 && Math.abs(dsty) < 16 )){
					state.state = State.ATTACK;
					e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS,0.3f, 1, Constants.Spells.PUNCH));
					e.changedInWorld();

				}
				p.direction.x = Math.signum(v.vx);
				p.direction.y = Math.signum(v.vy);
			}
		}
	}

	private interface Actuator {
		public void process();
	}

}
