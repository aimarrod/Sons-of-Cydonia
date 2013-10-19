package com.soc.game.attacks.processors;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.Renderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.GraphicsLoader;

public class AirBlastProcessor implements AttackProcessor{

		public Bag<Entity> hit;
		public float blastDuration, tick;
		public Renderer renderer;
		public Rectangle rect;
		public Circle hitbox;
		public float radius;
		
		
		@Mapper
		ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
		public AirBlastProcessor() {
			this.hit = new Bag<Entity>();
			this.renderer = GraphicsLoader.loadAirBlast();
			rect = new Rectangle();
			hitbox = new Circle();
			radius = Constants.Spells.AIR_BLAST_RADIUS_INCREASE;
			blastDuration = Constants.Spells.AIR_BLAST_TIME;
			tick = Constants.Spells.AIR_BLAST_TICK_INTERVAL;
		}

		@Override 
		public void process(Entity attack) {
			blastDuration -= SoC.game.world.delta;
			tick -= SoC.game.world.delta;
			if(tick <= 0){
				tick = Constants.Spells.AIR_BLAST_TICK_INTERVAL;
				radius += Constants.Spells.AIR_BLAST_RADIUS_INCREASE;
			}
			if(blastDuration <= 0){
				attack.deleteFromWorld();
			}
		}

		@Override
		public boolean collision(Entity attack, Entity victim) {
			Position attackpos = SoC.game.positionmapper.get(attack);
			Position victimpos = SoC.game.positionmapper.get(victim);
			Bounds victimbounds = SoC.game.boundsmapper.get(victim);
			rect.set((int)victimpos.x, (int)victimpos.y, victimbounds.width, victimbounds.height);
			hitbox.set(attackpos.x, attackpos.y, radius);
					
			return (!hit.contains(victim) && Intersector.overlaps(hitbox, rect));
		}

		@Override
		public void handle(Entity attack, Entity victim) {
			Attack a = SoC.game.attackmapper.get(attack);
			Position pos1 = SoC.game.positionmapper.get(attack);
			Position pos2 = SoC.game.positionmapper.get(victim	);
			hit.add(victim);
			if(SoC.game.damagemapper.has(victim)){
				SoC.game.damagemapper.get(victim).damage+=a.damage;
			}else{
				victim.addComponent(new Damage(a.damage, false));
				victim.changedInWorld();
			}
			Vector2 pushdirection = new Vector2();
			if(pos1.direction.x != 0){
				pushdirection.y = Math.signum(pos2.y - pos1.y);
			}
			if(pos1.direction.y != 0){
				pushdirection.x = Math.signum(pos2.x - pos1.x);
			}
			Debuff.addDebuff(victim, new Push(pushdirection, 100, 500));
		}

		@Override
		public void frame(Entity attack, SpriteBatch sprite) {
			Position pos = SoC.game.positionmapper.get(attack);
			sprite.draw(renderer.frame(SoC.game.world.delta), pos.x - radius*0.5f, pos.y - radius*0.5f, radius, radius);
		}

		@Override
		public void delete() {

		}
}
