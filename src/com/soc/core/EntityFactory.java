package com.soc.core;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.ai.bosses.BallistaAI;
import com.soc.ai.bosses.BlackMageAI;
import com.soc.ai.bosses.FireStoneMonsterAI;
import com.soc.ai.bosses.GaiaAI;
import com.soc.ai.bosses.GaiaAirAI;
import com.soc.ai.bosses.GaiaAvatarAI;
import com.soc.ai.bosses.GaiaDarkAI;
import com.soc.ai.bosses.GaiaFlameAI;
import com.soc.ai.bosses.KnightCaptainAI;
import com.soc.ai.bosses.MidMonsterAI;
import com.soc.ai.bosses.RightMonsterAI;
import com.soc.ai.bosses.SatanAI;
import com.soc.ai.normals.EyeballAI;
import com.soc.ai.normals.GoldBowKnightAI;
import com.soc.ai.normals.GoldKnightAI;
import com.soc.ai.normals.GreenKnightAI;
import com.soc.ai.normals.MaggotAI;
import com.soc.ai.normals.MeleeSkeletonAI;
import com.soc.ai.normals.SkeletonAI;
import com.soc.ai.normals.SlimeAI;
import com.soc.ai.normals.ZombiAI;
import com.soc.core.Constants.Spells;
import com.soc.core.Constants.World;
import com.soc.game.attacks.processors.AirCircleProcessor;
import com.soc.game.attacks.processors.AntiVenomFountain;
import com.soc.game.attacks.processors.ArrowProcessor;
import com.soc.game.attacks.processors.BiteProcessor;
import com.soc.game.attacks.processors.BlackMageAttackProcessor;
import com.soc.game.attacks.processors.BoneThrowProcessor;
import com.soc.game.attacks.processors.ChargeBossProcessor;
import com.soc.game.attacks.processors.ChargeProcessor;
import com.soc.game.attacks.processors.DaggerThrowProcessor;
import com.soc.game.attacks.processors.FireBreathProcessor;
import com.soc.game.attacks.processors.FireStoneProcessor;
import com.soc.game.attacks.processors.FlameProcessor;
import com.soc.game.attacks.processors.FlameWallProcessor;
import com.soc.game.attacks.processors.HarmfulEnemyProcessor;
import com.soc.game.attacks.processors.IcicleProcessor;
import com.soc.game.attacks.processors.MeteorProcessor;
import com.soc.game.attacks.processors.PoisonCloudProcessor;
import com.soc.game.attacks.processors.QuakeBladeProcessor;
import com.soc.game.attacks.processors.RedPushAttackProcessor;
import com.soc.game.attacks.processors.SlashProcessor;
import com.soc.game.attacks.processors.StompProcessor;
import com.soc.game.attacks.processors.TentaclesProcessor;
import com.soc.game.attacks.processors.TornadoProcessor;
import com.soc.game.attacks.processors.VenomSwordProcessor;
import com.soc.game.attacks.processors.WhirlbladeProcessor;
import com.soc.game.attacks.processors.WindbladeProcessor;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Buff;
import com.soc.game.components.Character;
import com.soc.game.components.Drop;
import com.soc.game.components.Enemy;
import com.soc.game.components.Feet;
import com.soc.game.components.Flying;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.Spawner;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.components.Wall;
import com.soc.game.states.benefits.Inmune;
import com.soc.game.states.benefits.Unmovable;
import com.soc.utils.GraphicsLoader;


public class EntityFactory {
	
	public static Entity loadCharacter(Position pos, Stats st, String clazz, Player player){
		Entity e = SoC.game.world.createEntity();
		
		Character animations = new Character();
		e.addComponent(pos);
		e.addComponent(st);
	    e.addComponent(animations);
		e.addComponent(new Velocity(0,0,Constants.Characters.VELOCITY));
		e.addComponent(player);
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new State(0));

	    if(clazz.equals(Constants.Characters.WARRIOR)){
	    	GraphicsLoader.loadWarrior(animations);
	    }
	    
		return e;
	}

	public static Entity createCharacter(float px, float py, int pz,float range, int damage, int type){
		Entity e = SoC.game.world.createEntity();
		Character animations = new Character();
		
	    e.addComponent(new Position(px,py, pz));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0,Constants.Characters.VELOCITY));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Stats(100, 100, 0, 100, 100, 100, 1, 0, 5, 5, 5, Constants.Spells.SLASH, new int[]{Constants.Spells.DAGGER_THROW, -1, -1, -1}, Constants.Characters.WARRIOR));
	    e.addComponent(new State(0));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(animations);
	    
	    if(type == Constants.Classes.HUNTER){
	    } else if(type == Constants.Classes.WARRIOR){
	    	GraphicsLoader.loadWarrior(animations);
	    } else if(type == Constants.Classes.MAGE){
	    	
	    } else {
	    	
	    }
	    
	    return e;
	}
	
	/*
	 * ENEMIES
	 */
	
	public static Entity createSkeleton(float px, float py, int pz,int damage){
		Entity e = SoC.game.world.createEntity();
	    e.addComponent(new Position(px,py, pz));
	    e.addComponent(new Velocity(0,0,100));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new State(1));
	    e.addComponent(new Stats(15, 0, 0, 15, 0, 0, 1, 0, 5, 5, 0, Constants.Spells.BONE_THROW, new int[]{}, Constants.Groups.SKELETONS));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new Enemy(10, new SkeletonAI()));
	    
	    Character animations = new Character();
	    GraphicsLoader.loadSkeleton(animations);
	    e.addComponent(animations);
	    
	    return e;
	}
	
	public static Entity createMeleeSkeleton(float px, float py, int pz,int damage){
		Entity e = SoC.game.world.createEntity();
	    e.addComponent(new Position(px,py, pz));
	    e.addComponent(new Velocity(0,0,100));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new State(1));
	    e.addComponent(new Stats(20, 0, 0, 20, 0, 0, 1, 0, 10, 5, 0, Constants.Spells.SLASH, new int[]{}, Constants.Groups.SKELETONS));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new Enemy(10, new MeleeSkeletonAI()));
	    
	    Character animations = new Character();
	    GraphicsLoader.loadMeleeSkeleton(animations);
	    e.addComponent(animations);
	    
	    return e;
	}
	
	public static Entity createBallista(float px, float py, int pz, int damage){
		Entity e = SoC.game.world.createEntity();
	    e.addComponent(new Position(px,py, pz));
	    e.addComponent(new Velocity(0,0,0));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new State(0));
	    e.addComponent(new Stats(30, 0, 0, 30, 0, 0, 1, 2, 0, 100, 0, Constants.Spells.ARROW, new int[]{},Constants.Groups.BALLISTAS));
	    e.addComponent(new Enemy(40, new BallistaAI()));
	    e.addComponent(new Feet(25, 25));
		Buff.addbuff(e, new Unmovable());

	    
	    Character animations = new Character();
	    GraphicsLoader.loadBallista(animations);
	    e.addComponent(animations);
	    
	    return e;
	}
	
	public static Entity createMaggot(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,200));
		e.addComponent(new Bounds(25, 25));
		e.addComponent(new State(1));
		e.addComponent(new Attack(new HarmfulEnemyProcessor(), 10));
		e.addComponent(new Feet(25, 25));
		e.addComponent(new Enemy(0, new MaggotAI()));
		e.addComponent(new Stats(
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.MAGGOTS));
		Character animations = new Character();
		GraphicsLoader.loadMaggot(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createSlime(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(64, 64));
		e.addComponent(new State(1));
		e.addComponent(new Attack(new HarmfulEnemyProcessor(), 10));
		e.addComponent(new Feet(30, 15));
		e.addComponent(new Enemy(25, new SlimeAI()));
		e.addComponent(new Stats(
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.SLIMES));
		Character animations = new Character();
		GraphicsLoader.loadSlime(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createZombie(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
	    e.addComponent(new Position(px,py, pz));
	    e.addComponent(new Velocity(0,0,100));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new State(1));
	    e.addComponent(new Stats(10, 0, 0, 10, 0, 0, 1, 1, 1, 1, 1, Constants.Spells.BITE, new int[]{}, Constants.Groups.ZOMBIES));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new Enemy(10, new ZombiAI()));
	    
		Character animations = new Character();
		GraphicsLoader.loadZombie(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGreenKnight(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new State(0));
		e.addComponent(new Enemy(50, new GreenKnightAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				Constants.Spells.CHARGE, 
				null,
				Constants.Groups.GREEN_KNIGHTS));
		Character animations = new Character();
		GraphicsLoader.loadGreenKnight(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGoldBowKnight(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new State(0));
		e.addComponent(new Enemy(50, new GoldBowKnightAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				Constants.Spells.ARROW, 
				null,
				Constants.Groups.GREEN_KNIGHTS));
		Character animations = new Character();
		GraphicsLoader.loadGoldBowKnight(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGoldKnight(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new State(0));
		e.addComponent(new Enemy(50, new GoldKnightAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				Constants.Spells.WHIRLBLADE, 
				null,
				Constants.Groups.GOLD_KNIGHTS));
		Character animations = new Character();
		GraphicsLoader.loadGoldKnight(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createKnightCaptain(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new State(0));
		e.addComponent(new Enemy(50, new KnightCaptainAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				Constants.Spells.SLASH, 
				null,
				Constants.Groups.KNIGHT_CAPTAIN));
		Character animations = new Character();
		GraphicsLoader.loadKnightCaptain(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createSatan(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
		e.addComponent(new State(1));
	    e.addComponent(new Stats(50, 0, 0, 50, 0, 0, 1, 1, 1, 1, 1, Constants.Spells.VENOMSWORD, new int[]{}, Constants.Groups.SATANS));
		e.addComponent(new Feet(32, 15));
		e.addComponent(new Enemy(5, new SatanAI()));
		Character animations = new Character();
		GraphicsLoader.loadSatan(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGaiaAir(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(128, 128));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new GaiaAirAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.GAIAS));
		Buff.addbuff(e, new Unmovable());
		Character animations = new Character();
		GraphicsLoader.loadGaiaAir(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGaiaDark(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(128, 128));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new GaiaDarkAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.GAIAS));
		Buff.addbuff(e, new Unmovable());
		Character animations = new Character();
		GraphicsLoader.loadGaiaDark(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGaiaFlame(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(128, 128));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new GaiaFlameAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.GAIAS));
		Buff.addbuff(e, new Unmovable());
		Character animations = new Character();
		GraphicsLoader.loadGaiaFlame(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGaia(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(128, 128));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new GaiaAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.GAIAS));
		Buff.addbuff(e, new Unmovable());
		Buff.addbuff(e, new Inmune());
		Character animations = new Character();
		GraphicsLoader.loadGaia(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createGaiaAvatar(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new GaiaAvatarAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.GAIAS));
		Character animations = new Character();
		GraphicsLoader.loadGaiaAvatar(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createEyeball(float x, float y, int z) {
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(x, y, z));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(32, 38));
		e.addComponent(new Feet(32, 38));
		e.addComponent(new Flying());
		e.addComponent(new State(1));
		e.addComponent(new Enemy(5, new EyeballAI()));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.EYEBALLS));
		Character animations = new Character();
		GraphicsLoader.loadEyeball(animations);
		e.addComponent(animations);
	   	
	   	return e;		
	}
	
	public static Entity createMidMonster(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(64, 128));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.RED_MONSTER));
	    e.addComponent(new Enemy(5, new MidMonsterAI()));
	    Character animations = new Character();
		GraphicsLoader.loadMidMonster(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createRightMonster(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,100));
		e.addComponent(new Bounds(64, 64));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.RIGHT_MONSTER));
	    e.addComponent(new Enemy(5, new RightMonsterAI()));
	    Character animations = new Character();
		GraphicsLoader.loadRightMonster(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createFireStoneMonster(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,0));
		e.addComponent(new Bounds(128, 128));
		e.addComponent(new Feet(32, 64));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new FireStoneMonsterAI()));
		e.addComponent(new Flying());
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.FIRE_STONE));
		Buff.addbuff(e, new Unmovable());
		Character animations = new Character();
		GraphicsLoader.loadFireStoneMonster(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	public static Entity createBlackMage(float px, float py, int pz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent(new Position(px, py, pz));
		e.addComponent(new Velocity(0,0,200));
		e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
		e.addComponent(new Feet(32, 10));
		e.addComponent(new State(0));
		e.addComponent(new Enemy(5, new BlackMageAI()));
		e.addComponent(new Flying());
		e.addComponent(new Stats(
				100, 
				0, 
				0, 
				100, 
				0, 
				0, 
				1, 
				0, 
				0, 
				0, 
				0, 
				0, 
				null,
				Constants.Groups.BLACK_MAGE));
		Character animations = new Character();
		GraphicsLoader.loadBlackMage(animations);
		e.addComponent(animations);
		
		return e;
	}
	
	
	
	/*
	 * ATTACKS
	 */
		
	public static Entity createDaggerThrow(String group, Position pos, int damage, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(pos.x,pos.y, pos.z) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(Constants.Spells.DAGGER_SPEED*dir.x, Constants.Spells.DAGGER_SPEED*dir.y,900) );
	   	e.addComponent( new Flying() );
	   	e.addComponent( new Attack(new DaggerThrowProcessor(pos), damage) );
	   	
	   	return e;
	}
	
	public static Entity createArrow(String group, Position pos, int damage, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
		int addX=0;
		int addY=0;
		if(Math.abs(pos.direction.x)>0){
			addY=26;
		}if(Math.abs(pos.direction.y)>0){
			addX=26;	
		}

		e.addComponent( new Position(pos.x+addX,pos.y+addY, pos.z, pos.direction) );
		e.addComponent( new Bounds(1,1));
		e.addComponent( new Velocity(Constants.Spells.ARROW_SPEED*dir.x, Constants.Spells.ARROW_SPEED*dir.y,Constants.Spells.ARROW_SPEED) );
	   	e.addComponent( new Flying() );
	   	e.addComponent( new Attack(new ArrowProcessor(), damage) );
	   	
	   	return e;
	}
	public static Entity createIcicle(String group ,Position pos, int damage, int range, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x,pos.y, pos.z) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new IcicleProcessor(dir, range), damage ) );
	   	
	   	return e;
	}
	
	public static Entity createFireball(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x, pos.y, pos.z) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new IcicleProcessor(dir, range), damage) );
	   	
	   	return e;
	}
	
	public static Entity createBite(String group, Position pos, int damage, int range){
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(pos.x + Constants.Characters.WIDTH * pos.direction.x, pos.y + Constants.Characters.HEIGHT * pos.direction.y, pos.z));
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
	   	e.addComponent( new Attack(new BiteProcessor(pos.direction, range), damage) );
	   	
	   	return e;
	}
	
	public static Entity createVenomSword(String group, Position pos, int damage, int range){
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(pos.x + Constants.Characters.WIDTH * pos.direction.x, pos.y + Constants.Characters.HEIGHT * pos.direction.y, pos.z));
		e.addComponent( new Bounds(Constants.Characters.WIDTH*3, Constants.Characters.HEIGHT*3) );
	   	e.addComponent( new Attack(new VenomSwordProcessor(pos.direction, range), damage) );
	   	
	   	return e;
	}
	public static Entity createCharge(Entity source, String group, Position pos, int damage){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x-Spells.CHARGE_BOX*0.5f, pos.y-Spells.CHARGE_BOX*0.5f, pos.z, pos.direction.cpy()) );
		e.addComponent( new Bounds(Spells.CHARGE_BOX, Spells.CHARGE_BOX) );
		e.addComponent( new Velocity(300*pos.direction.x, 300*pos.direction.y, Constants.Spells.CHARGE_SPEED) );
	   	e.addComponent( new Attack(new ChargeProcessor(source, Constants.Spells.CHARGE_DURATION), damage) );
	   	
	   	return e;
	}
	
	public static Entity createChargeBoss(Entity source, String group, Position pos, int damage){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x-Spells.CHARGE_BOX*0.5f, pos.y-Spells.CHARGE_BOX*0.5f, pos.z, pos.direction.cpy()) );
		e.addComponent( new Bounds(Spells.CHARGE_BOX, Spells.CHARGE_BOX) );
		e.addComponent( new Velocity(300*pos.direction.x, 300*pos.direction.y, Constants.Spells.CHARGE_SPEED) );
	   	e.addComponent( new Attack(new ChargeBossProcessor(source, Constants.Spells.CHARGE_DURATION), damage) );
	   	
	   	return e;
	}
	
	public static Entity createSpawner(float x, float y, int z, int width, int height, String type, int max, int range, float interval, boolean respawn){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, z) );
		e.addComponent( new Bounds(width, height));
		e.addComponent( new Spawner(type, max, range, interval,respawn));
		
		return e;
	}


	public static Entity createSlash(Entity source ,Position pos, int damage) {
		Entity e=SoC.game.world.createEntity();
		
		Feet feet = SoC.game.feetmapper.get(source);
		e.addComponent( new Velocity(0, 0, Constants.Spells.DAGGER_SPEED) );
		float centerx = pos.x + feet.width*0.5f;
		float centery = pos.y + feet.heigth*0.5f;
		if(pos.direction.x != 0){
			e.addComponent( new Position(centerx+feet.width*0.5f*Math.signum(pos.direction.x), pos.y+feet.heigth*1.5f, pos.z, pos.direction.cpy()) );
			e.addComponent( new Bounds((int) ((int) World.TILE_SIZE*Math.signum(pos.direction.x)*1.5f), (int) World.TILE_SIZE) );

		} else {
			e.addComponent( new Position(centerx-feet.width*0.8f, centery+feet.heigth*((pos.direction.y<0)?0.4f:1.1f), pos.z, pos.direction) );
			e.addComponent( new Bounds((int)(World.TILE_SIZE*2.4f), (int) (World.TILE_SIZE*((pos.direction.y<0)?1.0f:1.7f)*Math.signum(pos.direction.y))));

		}
	   	e.addComponent( new Attack(new SlashProcessor(), damage) );
	   	
	   	return e;		
	}
	
	public static Entity createWhirlblade(Position pos, Bounds bon,int damage) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Velocity(0, 0, Constants.Spells.DAGGER_SPEED) );
		e.addComponent(new Position(pos.x, pos.y, pos.z));
	   	e.addComponent( new Attack(new WhirlbladeProcessor(pos, bon), damage) );
	   	
	   	return e;		
	}
	
	public static Entity createPoisonCloud(Position pos, Bounds bon) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(pos.x+bon.width*0.5f, pos.y+bon.height+0.5f, pos.z));
	   	e.addComponent( new Attack(new PoisonCloudProcessor(), 0) );
	   	
	   	return e;		
	}
	
	public static Entity createQuake(Position pos, Feet feet, int damage) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Velocity(0, 0, 0) );
		e.addComponent(new Position(pos.x+feet.width*0.5f, pos.y+feet.heigth+0.5f, pos.z, pos.direction.cpy()));
	   	e.addComponent( new Attack(new QuakeBladeProcessor(), damage) );
	   	
	   	return e;		
	}
	
	public static Entity createTornado(float x, float y, int z, Vector2 direction, float speedFactor) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Velocity(Constants.Spells.TORNADO_SPEED*direction.x*speedFactor, Constants.Spells.TORNADO_SPEED*direction.y*speedFactor, 0) );
		e.addComponent( new Position(x, y, z, direction));
		e.addComponent( new Bounds(44, 32) );
		e.addComponent( new Flying() );
	   	e.addComponent( new Attack(new TornadoProcessor(), 0) );
	   	
	   	return e;		
	}
	
	public static Entity createFireStone(float x, float y, int z, Vector2 direction) {
		Entity e=SoC.game.world.createEntity();
		e.addComponent( new Velocity(Constants.Spells.FIREBREATH_SPEED*direction.x, Constants.Spells.FIREBREATH_SPEED*direction.y, 0) );
		e.addComponent( new Position(x, y, z, direction));
		e.addComponent( new Bounds(84, 75) );
	   	e.addComponent( new Attack(new FireStoneProcessor(), 0) );
	   	
	   	return e;		
	}
	
	public static Entity createStomp(float x, float y, int z, int damage) {
		Entity e=SoC.game.world.createEntity();
		e.addComponent( new Position(x, y, z));
	   	e.addComponent( new Attack(new StompProcessor(), damage) );
	   	
	   	return e;		
	}
	
	public static Entity createFireBreath(float x, float y, int z,int damage, Vector2 direction) {
		Entity e=SoC.game.world.createEntity();
		e.addComponent( new Velocity(Constants.Spells.TORNADO_SPEED*direction.x, Constants.Spells.TORNADO_SPEED*direction.y, 0) );
		e.addComponent( new Position(x, y, z, direction));
		e.addComponent( new Bounds(84, 75) );
	   	e.addComponent( new Attack(new FireBreathProcessor(), damage) );	   	
	   	return e;		
	}
	
	public static Entity createFlame(float x, float y, int z,int damage) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, z));
		e.addComponent( new Bounds(64,64) );
	   	e.addComponent( new Attack(new FlameProcessor(), damage) );
	   	
	   	return e;		
	}
	public static Entity createRedPush(float x, float y, int z,int damage) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, z));
		e.addComponent( new Bounds(200, 200) );
	   	e.addComponent( new Attack(new RedPushAttackProcessor(), damage) );
	   	
	   	return e;		
	}
	
	public static Entity createFlameWall(Entity owner,float x, float y, int z) {
		Entity e=SoC.game.world.createEntity();
		System.out.println("paso por aqui");
		e.addComponent( new Position(x*Constants.World.TILE_SIZE, y*Constants.World.TILE_SIZE, z) );
		e.addComponent( new Bounds((int)Constants.World.TILE_SIZE, (int)Constants.World.TILE_SIZE));;
	   	e.addComponent( new Attack(new FlameWallProcessor(), 0) );
	   	SoC.game.wallmanager.block(owner, e);
	   	
	   	return e;		
	}
	
	public static Entity createAntiVenomFountain(float x, float y, int z, Vector2 direction) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, z, direction));
		e.addComponent( new Bounds(32, 70) );
	   	e.addComponent( new Attack(new AntiVenomFountain(), 0) );
	   	
	   	return e;		
	}
	
	public static Entity createMeteor(float x, float y, int z) {
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, z));
		e.addComponent( new Bounds(32, 32) );
	   	e.addComponent( new Attack(new MeteorProcessor(), 20) );
	   	
	   	return e;		
	}

	public static Entity createWindblade(String group, Position pos, int damage,
			Position pos2) {
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Velocity(Constants.Spells.WINDBLADE_SPEED*pos.direction.x, Constants.Spells.WINDBLADE_SPEED*pos.direction.y, 0) );
		e.addComponent( new Position(pos.x, pos.y, pos.z, pos.direction) );
		e.addComponent( new Bounds(48, 48) );
	   	e.addComponent( new Attack(new WindbladeProcessor(), damage) );
	   	
		return e;
	}
	
	public static Entity createBoneThrow(String group, Position pos, int damage,
			Position pos2) {
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Velocity(Constants.Spells.BONE_THROW_SPEED*pos.direction.x, Constants.Spells.BONE_THROW_SPEED*pos.direction.y, 0) );
		e.addComponent( new Position(pos.x, pos.y, pos.z, pos.direction) );
		e.addComponent( new Bounds(32, 32) );
	   	e.addComponent( new Attack(new BoneThrowProcessor(), damage) );
	   	
		return e;
	}
	
	public static Entity createTentacles(float x, float y, int z, int damage){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, z) );
		e.addComponent( new Attack(new TentaclesProcessor(), damage));
		
		return e;
	}
	
	
	public static Entity createCircle(float x, float y, int posz){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Position(x, y, posz) );
		e.addComponent( new Bounds(50, 50));
		e.addComponent( new Attack(new AirCircleProcessor(), 0) );
			
		return e;
	}
	
	public static Entity createParadigmShift(Entity source) {
		Position pos = SoC.game.positionmapper.get(source);
		Bounds bon = SoC.game.boundsmapper.get(source);
		Velocity vel = SoC.game.velocitymapper.get(source);
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Position(pos.x + bon.width*0.5f, pos.y+bon.height*0.5f, pos.z) );
		e.addComponent( new Velocity(vel.vx, vel.vy, vel.speed));
		e.addComponent( new Bounds(64, 64));
		e.addComponent( new Flying() );
		e.addComponent( new Attack(new BlackMageAttackProcessor(source), SoC.game.statsmapper.get(source).intelligence*2) );
		
		return e;
	}
	
	public static Entity createWall(Entity source, int x, int y, int z){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Position(x*Constants.World.TILE_SIZE, y*Constants.World.TILE_SIZE, z) );
		e.addComponent( new Bounds((int)Constants.World.TILE_SIZE, (int)Constants.World.TILE_SIZE));
		e.addComponent( new Wall() );
		
		SoC.game.groupmanager.add(e, Constants.Groups.WALLS);
		SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL + z);
		SoC.game.wallmanager.block(source, e);
		
		return e;
		
	}
	
	public static Entity createItem(int number, float x, float y, int z){
		Entity e = SoC.game.world.createEntity();
		
		e.addComponent( new Position(x + Constants.World.TILE_SIZE*0.5f, y + Constants.World.TILE_SIZE*0.5f, z) );
		e.addComponent( new Bounds(Constants.Items.ITEM_SIZE, Constants.Items.ITEM_SIZE));
		e.addComponent( new Drop(number) );
		
		SoC.game.groupmanager.add(e, Constants.Groups.ITEMS);
		SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL + z);
		
		return e;
	}
	
}
