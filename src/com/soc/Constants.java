package com.soc;

public class Constants {
	public class Attacks{
		 public static final int DAGGER_ATTACK = 0;
		 public static final int SWORD_ATTACK=1;
		 public static final int ARCH_ATTACK=2;
		 
		 public static final int DAGGER_SPEED = 500;
	}
	
	public class Characters{
		public static final int WIDTH_PIXELS = 32;
		public static final int HEIGHT_PIXELS = 50;
	}
	
	public class Groups{
		public static final String PLAYER = "player";
		public static final String ENEMIES = "enemy";
		public static final String PLAYER_PROJECTILES = "projectile";
	}
	
	public class World{
		public static final int TILE_SIZE = 32;
	}
}