package com.soc.game.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.game.graphics.DirectionalRenderer;

public class RenderSystem extends VoidEntitySystem{
	
	private final static float UNITSCALE = 1/1f;

	@Mapper ComponentMapper<Character> cm;
	@Mapper ComponentMapper<Bounds> bm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Attack> am;
	@Mapper ComponentMapper<State> sm;
	@Mapper ComponentMapper<Player> plm;

	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private int layers;
	private SpriteBatch batch;
	

	
	public RenderSystem(OrthographicCamera camera){
		this.camera = camera;
		batch = new SpriteBatch();
	}
	
	public void changeMap(TiledMap map){
		this.renderer = new OrthogonalTiledMapRenderer(map, UNITSCALE);
		this.layers = Integer.parseInt(map.getProperties().get("layers", String.class));

	}
	
	public void render(){
		
		renderer.render(new int[]{0,1});
	}
	
	@Override
	protected void begin() {
	}

	@Override
	protected void processSystem() {
		
		
		renderer.setView(camera);
		for(int i = 0; i < layers; i++){
			int current = 4*i;
			renderer.render(new int[]{current, current+1, current+2});
			
			batch.begin();
			batch.setProjectionMatrix(camera.combined);
			List<Entity> list = SoC.game.levelmanager.getEntitiesOfLevel(Constants.Groups.LEVEL+i);
			for(int j = 0; j < list.size(); j++){
				Entity e = list.get(j);
				if(cm.has(e)){
					renderCharacter(e);
				} else {
					if(am.has(e)){
						am.get(e).processor.frame(e, batch);
					}
				}
			}
			batch.end();
			
			renderer.render(new int[]{current+3});
		}
	}
	
	@Override
	protected void end() {
	}
	
	private void renderCharacter(Entity e){
		State state = sm.get(e);
		Position pos = pm.get(e);
		Bounds bon = bm.get(e);
		Character animations = cm.get(e);
		
		DirectionalRenderer r = null;
		if(state.state == State.ATTACK){
			r = animations.attack;
			if(((DirectionalAnimatedRenderer)r).isFinished()){
				r.time = 0;
			}
		}
		if(state.state == State.WALK){
			r = animations.movement;
		}
		
		if(state.state == State.IDLE){
			r = animations.idle;
		}
		if(state.state==State.DYING){
			r=animations.death;
		}
		else{
		float angle = pos.direction.angle();
		if(angle%90 != 0){
			if(angle<90f || angle>270f){
				angle=0f;
			} else {
				angle=180f;
			}
		}
		r.direction = ((int) angle/90 + 3) % 4;
		}

		batch.setColor(r.r, r.g, r.b, r.a);
		batch.draw(r.frame(world.delta), pos.x+r.ox, pos.y+r.oy);
	}
}
