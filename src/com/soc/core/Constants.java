package com.soc.core;

public class Constants {

	public static class Characters{
		public static final int WIDTH = 32;
		public static final int HEIGHT = 47;
		public static final int FEET_WIDTH = 32;
		public static final int FEET_HEIGTH = 10;
		public static final int VELOCITY=150;
		public static final String WARRIOR = "warrior";
	}
	
	public static class Groups{
		public static final String PLAYERS = "player";
		public static final String ENEMIES = "enemy";
		
		public static final String MAGGOTS = "maggot";
		public static final String SLIMES = "slime";
		public static final String SKELETONS = "skeleton";
		public static final String BALLISTAS= "ballista";
		public static final String ZOMBIES= "zombie";
		public static final String SATANS= "satan";
		public static final String GAIA_AIR = "gaia-air";
		public static final String EYEBALLS = "eyeball";
		
		public static final String ENEMY_ATTACKS = "enemyattack";
		public static final String PLAYER_ATTACKS = "playerattack";
		public static final String MAP_BOUND = "mapbound"; //The ones which dissapear with the map
		public static final String LEVEL = "level";
		public static final String CHARACTERS = "characters";
		public static final String PROJECTILES = "projectiles";
		public static final String DESTROYABLE_PROJECTILES="destroyable_projectiles";
	}
	
	public static class World{
		public static final float TILE_SIZE = 32f;
		public static final float TILE_FACTOR = 0.03125f;
		public static final int TILE_WALKABLE = 0;
		public static final int TILE_OBSTACLE = 1; 
		public static final int TILE_GATE = 2;
		public static final int TILE_LEVEL_CHANGE = 3;
		public static final int TILE_STAIRS = 4;
		public static final int TILE_UNWALKABLE = 5;
		public static final int TILE_LAVA = 6;
		public static final int TILE_HOLE = 7;
		public static final int LAYERS_PER_LEVEL = 4;
	}
	
	public static class Configuration{
		public static final String RESOURCE_DIR = "resources/";
		public static final String MUSIC_DIR = "resources/music/";
		public static final String MAP_DIR = "resources/map/";
		public static final String LEVEL_DIR = "resources/level/";
		public static final float LABEL_SPEED = 50;
		public static final float LABEL_DURATION = 0.6f;
	}
	
	public static class Classes{
		public static final int NONE = 0;
		public static final int WARRIOR = 1;
		public static final int HUNTER = 2;
		public static final int MAGE = 3;
	}
	
	public static class Spells{
		public static final int SLASH = 0;
		public static final int DAGGER_THROW = 1;
		public static final int ICICLE = 2;
		public static final int FIREBALL = 3;
		public static final int ICEBLAST = 4;
		public static final int PUNCH= 5;
		public static final int CHARGE=6;
		public static final int ARROW=7;
		public static final int WHIRLBLADE=8;
		public static final int QUAKEBLADE = 9;
		public static final int BITE = 10;
		
		public static final int SPELL_NUMBER = 11;
		
		public static final int TORNADO_RANGE = 1000;
		public static final int TORNADO_SPEED = 500;
		public static final int BALLISTA_FIRE_RATE = 1;
		public static final int DAGGER_SPEED = 900;
		public static final int DAGGER_RANGE = 700;
		public static final int ARROW_SPEED = 900;
		public static final int CHARGE_SPEED = 700;
		public static final float CHARGE_DURATION = 1.5f;
		public static final int CHARGE_BOX = 70;
		public static final float QUAKEBLADE_TICK_INTERVAL = 0.3f;
		public static final int QUAKEBLADE_RADIUS_INITIAL = 30;
		public static final int QUAKEBLADE_RADIUS_INCREASE = 16;
		public static final float SPIN_DURATION = 3.5f;
		public static final float SPIN_RADIUS = 75f;
		public static final float POISON_CLOUD_DURATION = 6f;
		public static final float POISON_CLOUD_RADIUS = 100;
	}
	
	
	public static class Attributes{
		public final static String MAP = "map"; 
		public final static String MUSIC = "music";
		public final static String POSITION = "position";
		public final static String INVENTORY = "inventory";
		public final static String STATS = "music";
		public final static String CLASS = "class";
	}
	
	public static class Tags{
		public final static String PLAYER = "player";
	}
		
	public static class Items{
		public final static int INVENTORY_SIZE=20;
		public final static int ITEM_NUMBER=5;
		public final static int NONE=0;
		public final static int AXE=1;
		public final static int ARMOR=2;
		public final static int HEALTH_POTION=3;
		public final static int MANA_POTION=4;

	}
	
	public static class Alteration{
		public final static float BURN_DURATION=5f;
		public final static float BURN_TICK_INTERVAL=1f;
		public final static int BURN_DAMAGE = 10;
		public final static float POISON_DURATION = 10f;
		public final static float POISON_TICK_INTERVAL = 1f;
		public final static int POISON_DAMAGE = 5;
	}
	
	public static class Buff{
		public final static float SHIELD_DURATION=10F;
		public final static float SHIELD_GAINHEALTH=50F;
	}
	
}
