package com.soc.game.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.core.Constants.World;
import com.soc.game.alterations.Burn;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Debuff;
import com.soc.game.components.Enemy;
import com.soc.game.components.Expires;
import com.soc.game.components.Feet;
import com.soc.game.components.Flying;
import com.soc.game.components.Harmful;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.map.Stairs;

public class CollisionSystem extends VoidEntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Bounds> bm;
	@Mapper
	ComponentMapper<Stats> sm;
	@Mapper
	ComponentMapper<State> stm;
	@Mapper
	ComponentMapper<Attack> am;
	@Mapper
	ComponentMapper<Velocity> vm;
	@Mapper
	ComponentMapper<Enemy> em;
	@Mapper
	ComponentMapper<Feet> fm;
	@Mapper
	ComponentMapper<Player> plm;
	@Mapper
	ComponentMapper<Flying> flm;
	@Mapper
	ComponentMapper<Harmful> hm;

	private Bag<CollisionGroup> collisionGroups;

	public CollisionSystem() {
		super();
	}

	@Override
	public void initialize() {
		collisionGroups = new Bag<CollisionGroup>();
		collisionGroups.add(new AttackDestroyableProjectileCollision());
		collisionGroups.add(new AttackCollision(
				Constants.Groups.PLAYER_ATTACKS, Constants.Groups.ENEMIES));
		collisionGroups.add(new AttackCollision(Constants.Groups.ENEMY_ATTACKS,
				Constants.Groups.PLAYERS));
		collisionGroups.add(new CharacterCollision());
		collisionGroups.add(new CharacterMapCollision());
		collisionGroups.add(new ProjectileMapCollision());
		
	}

	@Override
	protected void processSystem() {
		for (int i = 0; i < collisionGroups.size(); i++) {
			collisionGroups.get(i).processCollisions();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	private interface CollisionGroup {
		public void processCollisions();
	}
	

	private class CharacterCollision implements CollisionGroup {
		private ImmutableBag<Entity> characters;

		public CharacterCollision() {
			characters = SoC.game.groupmanager
					.getEntities(Constants.Groups.CHARACTERS);
		}

		@Override
		public void processCollisions() {
			for (int i = 0; i < characters.size(); i++) {
				for (int j = 0; j < characters.size(); j++) {
					process(characters.get(i), characters.get(j));
				}
			}
		}

		public void process(Entity e, Entity other) {
			if (e == other || stm.get(e).state == State.CHARGING)
				return;
			Position pos = pm.get(e);
			Velocity v = vm.get(e);
			Feet feet = fm.get(e);
			Position otherpos = pm.get(other);
			Feet otherfeet = fm.get(other);

			Rectangle nexty = new Rectangle(pos.x, pos.y + v.vy * world.delta,
					feet.width, feet.heigth);
			Rectangle nextx = new Rectangle(pos.x + v.vx * world.delta, pos.y,
					feet.width, feet.heigth);
			Rectangle current = new Rectangle(pos.x, pos.y, feet.width, feet.heigth);
			Rectangle otherrect = new Rectangle(otherpos.x, otherpos.y,
					otherfeet.width, otherfeet.heigth);
			if (nexty.overlaps(otherrect)) {
				v.vy = 0;
			}
			if (nextx.overlaps(otherrect)) {
				v.vx = 0;
			}
			if(current.overlaps(otherrect)){
				v.vx = v.speed * (Math.abs(otherrect.x - current.x));
				v.vy = v.speed * (Math.abs(otherrect.y - current.y));
			}
		}
	}

	private class ProjectileMapCollision implements CollisionGroup {
		private ImmutableBag<Entity> attacks;

		public ProjectileMapCollision() {
			attacks = SoC.game.groupmanager
					.getEntities(Constants.Groups.PROJECTILES);
		}

		@Override
		public void processCollisions() {
			for (int i = 0; i < attacks.size(); i++) {
				process(attacks.get(i));
			}
		}

		public void process(Entity e) {
			Position pos = pm.get(e);
			Bounds b = bm.get(e);
			Attack a = am.get(e);

			int centerx = (int) ((pos.x + b.width * 0.5) * World.TILE_FACTOR);
			int centery = (int) ((pos.y + b.height * 0.5) * World.TILE_FACTOR);

			if (SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_OBSTACLE) {
				e.deleteFromWorld();
				a.processor.delete();
				return;
			}
			
			if (!flm.has(e) && SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_UNWALKABLE) {
				e.deleteFromWorld();
				a.processor.delete();
				return;
			}

			if (SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_STAIRS) {
				SoC.game.groupmanager.remove(e, Constants.Groups.LEVEL + pos.z);
				pos.z = ((Stairs) SoC.game.map.tiles[pos.z][centerx][centery]).level - 1;
				SoC.game.groupmanager.add(e, Constants.Groups.LEVEL + pos.z);
			}
		}
	}

	private class CharacterMapCollision implements CollisionGroup {
		private ImmutableBag<Entity> enemies;

		public CharacterMapCollision() {
			enemies = SoC.game.groupmanager
					.getEntities(Constants.Groups.ENEMIES);
		}

		@Override
		public void processCollisions() {
			process(SoC.game.player);
			for (int i = 0; i < enemies.size(); i++) {
				process(enemies.get(i));
			}
		}

		protected void process(Entity e) {

			State st = stm.get(e);
			Position pos = pm.get(e);
			Velocity v = vm.get(e);
			Feet feet = fm.get(e);
			
			if(st.state == State.FALLING || st.state == State.DYING) return;

			int nextleft = (int) ((pos.x + v.vx * world.delta + feet.width) * World.TILE_FACTOR);
			int nextright = (int) ((pos.x + v.vx * world.delta) * World.TILE_FACTOR);
			int nextup = (int) ((pos.y + v.vy * world.delta + feet.heigth) * World.TILE_FACTOR);
			int nextdown = (int) ((pos.y + v.vy * world.delta) * World.TILE_FACTOR);

			int up = (int) ((pos.y + feet.heigth) * World.TILE_FACTOR);
			int rigth = (int) ((pos.x + feet.width) * World.TILE_FACTOR);
			int left = (int) ((pos.x) * World.TILE_FACTOR);
			int down = (int) ((pos.y) * World.TILE_FACTOR);

			int centerx = (int) ((pos.x + feet.width * 0.5) * World.TILE_FACTOR);
			int centery = (int) ((pos.y + feet.heigth * 0.5) * World.TILE_FACTOR);

			if (SoC.game.map.tiles[pos.z][nextright][up].type == World.TILE_OBSTACLE
					|| SoC.game.map.tiles[pos.z][nextleft][up].type == World.TILE_OBSTACLE
					|| SoC.game.map.tiles[pos.z][nextright][down].type == World.TILE_OBSTACLE
					|| SoC.game.map.tiles[pos.z][nextleft][down].type == World.TILE_OBSTACLE) {
				v.vx = 0;
			}
			if (SoC.game.map.tiles[pos.z][rigth][nextup].type == World.TILE_OBSTACLE
					|| SoC.game.map.tiles[pos.z][left][nextup].type == World.TILE_OBSTACLE
					|| SoC.game.map.tiles[pos.z][rigth][nextdown].type == World.TILE_OBSTACLE
					|| SoC.game.map.tiles[pos.z][left][nextdown].type == World.TILE_OBSTACLE) {
				v.vy = 0;
			}
			
			if(!flm.has(e)){
				if (SoC.game.map.tiles[pos.z][nextright][up].type == World.TILE_UNWALKABLE
						|| SoC.game.map.tiles[pos.z][nextleft][up].type == World.TILE_UNWALKABLE
						|| SoC.game.map.tiles[pos.z][nextright][down].type == World.TILE_UNWALKABLE
						|| SoC.game.map.tiles[pos.z][nextleft][down].type == World.TILE_UNWALKABLE) {
					v.vx = 0;
				}
				if (SoC.game.map.tiles[pos.z][rigth][nextup].type == World.TILE_UNWALKABLE
						|| SoC.game.map.tiles[pos.z][left][nextup].type == World.TILE_UNWALKABLE
						|| SoC.game.map.tiles[pos.z][rigth][nextdown].type == World.TILE_UNWALKABLE
						|| SoC.game.map.tiles[pos.z][left][nextdown].type == World.TILE_UNWALKABLE) {
					v.vy = 0;
				}
			}
			
			if (SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_STAIRS) {
				pos.z = ((Stairs) SoC.game.map.tiles[pos.z][centerx][centery]).level - 1;
				SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL
						+ pos.z);
				return;
			}
			
			if (SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_LAVA) {
				Debuff.addDebuff(e, new Burn());
				return;
			}
			
			if (SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_HOLE) {
				st.state=State.FALLING;
				pos.x += pos.direction.x*World.TILE_SIZE;
				pos.y += pos.direction.y*World.TILE_SIZE;
				e.addComponent(new Expires(1));
				e.changedInWorld();
				v.vx=0;
				v.vy=0;
				return;
			}

			if (plm.has(e)) {
				if (SoC.game.map.tiles[pos.z][centerx][centery].type == World.TILE_GATE) {
				}
			}

		}
	}

	private class AttackCollision implements CollisionGroup {
		private ImmutableBag<Entity> attacks;
		private ImmutableBag<Entity> receivers;

		public AttackCollision(String attackGroup, String receiverGroup) {
			attacks = world.getManager(GroupManager.class).getEntities(
					attackGroup);
			receivers = world.getManager(GroupManager.class).getEntities(
					receiverGroup);
		}

		@Override
		public void processCollisions() {
			for (int a = 0; attacks.size() > a; a++) {

				Entity atk = attacks.get(a);
				Attack attack = am.get(atk);

				for (int b = 0; receivers.size() > b; b++) {
					Entity enemy = receivers.get(b);
					if (stm.get(enemy).state != State.DYING &&  attack.processor.collision(atk, enemy)) {
						attack.processor.handle(atk, enemy);
					}
				}

			}

		}
	}
	
	private class AttackDestroyableProjectileCollision implements CollisionGroup {
		private ImmutableBag<Entity> attacks;
		private ImmutableBag<Entity> receivers;

		public AttackDestroyableProjectileCollision() {
			attacks = world.getManager(GroupManager.class).getEntities(
					Constants.Groups.PLAYER_ATTACKS);
			receivers = world.getManager(GroupManager.class).getEntities(
					Constants.Groups.DESTROYABLE_PROJECTILES);
		}

		@Override
		public void processCollisions() {
			for (int a = 0; attacks.size() > a; a++) {

				Entity atk = attacks.get(a);
				Attack attack = am.get(atk);

				for (int b = 0; receivers.size() > b; b++) {
					Entity enemy = receivers.get(b);
					if (attack.processor.collision(atk, enemy)) {
						enemy.deleteFromWorld();
					}
				}

			}

		}
	}

}
