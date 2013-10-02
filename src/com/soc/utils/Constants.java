package com.soc.utils;

public class Constants {

	public class Characters{
		public static final int WIDTH_PIXELS = 32;
		public static final int HEIGHT_PIXELS = 50;
	}
	
	public class Groups{
		public static final String PLAYERS = "player";
		public static final String ENEMIES = "enemy";
		public static final String SKELETONS = "skeleton";
		public static final String ENEMY_ATTACKS = "enemyattack";
		public static final String PLAYER_ATTACKS = "playerattack";
	}
	
	public class World{
		public static final float TILE_SIZE = 32f;
		public static final float TILE_FACTOR = 0.03125f;
		public static final int TILE_WALKABLE = 0;
		public static final int TILE_OBSTACLE = 1;
		public static final int TILE_MAP_CHANGE = 2;
		public static final int TILE_LEVEL_CHANGE = 3;
	}
	
	public class Configuration{
		public static final String RESOURCE_DIR = "resources/";
		public static final String MUSIC_DIR = "resources/music/";
		public static final String MAP_DIR = "resources/map/";
		public static final String LEVEL_DIR = "resources/level/";
	}
	
	public class Classes{
		public static final int NONE = 0;
		public static final int WARRIOR = 1;
		public static final int HUNTER = 2;
		public static final int MAGE = 3;
	}
	
	public class Spells{
		public static final int ARROW = 0;
		public static final int DAGGER_THROW = 1;
		public static final int ICICLE = 2;
		public static final int FIREBALL = 3;
		public static final int ICEBLAST = 4;
		public static final int PUNCH=5;
		
		public static final int SPELL_NUMBER = 6;
		public static final int DAGGER_SPEED=900;
	}
	
}
