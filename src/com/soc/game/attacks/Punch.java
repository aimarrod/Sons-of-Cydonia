package com.soc.game.attacks;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.DamageReceived;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.Globals;

public class Punch implements AttackProcessor{
	public Bag<Entity> hit;
	public float range;
	@Mapper
	ComponentMapper<DamageReceived> dm = Globals.world.getMapper(DamageReceived.class);
	public Punch(Vector2 dir, float range) {
		hit = new Bag<Entity>();
		this.range = range;
	}

	@Override 
	public void process(Entity e, Position p, Bounds b, Velocity v, float delta) {
	}

	@Override
	public boolean collision(Entity e, Position mypos, Bounds mybounds, Position otherpos,
			Bounds otherbounds) {
		return (!hit.contains(e) && mypos.x < otherpos.x + otherbounds.width && mypos.x + mybounds.height > otherpos.x && mypos.y < otherpos.y + otherbounds.height && mypos.y + mybounds.height > otherpos.y);
	}


	@Override
	public void handle(Entity e, Attack a, Stats s) {
		hit.add(e);
		//s.health -= a.damage;
		//DamageReceived damageReceived=e.getComponent(DamageReceived.class);
		//if(damageReceived==null){
		if(dm.has(e)){
			dm.get(e).damage+=a.damage;
		}else{
			e.addComponent(new DamageReceived(a.damage));
			e.changedInWorld();
		}
		//}else{
		//	damageReceived.damage+=a.damage;
		//}
	}

	@Override
	public void frame(float delta, SpriteBatch sprite, float posX, float posY) {
		// TODO Auto-generated method stub
		
	}
}
