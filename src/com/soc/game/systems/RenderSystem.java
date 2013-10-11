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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.alterations.Burn;
import com.soc.game.alterations.Poison;
import com.soc.game.alterations.Push;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Debuff;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.game.graphics.Renderer;
import com.soc.utils.FloatingText;

public class RenderSystem extends VoidEntitySystem{
	
	private final static float UNITSCALE = 1/1f;

	@Mapper ComponentMapper<Character> cm;
	@Mapper ComponentMapper<Bounds> bm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Attack> am;
	@Mapper ComponentMapper<State> sm;
	@Mapper ComponentMapper<Player> plm;
	@Mapper ComponentMapper<Debuff> dm;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private int layers;
	private SpriteBatch batch;
	private BitmapFont font;
	
	public Bag<FloatingText> texts;
	
	

	
	public RenderSystem(OrthographicCamera camera){
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.texts = new Bag<FloatingText>();
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
		batch.begin();
		font.setColor(0, 0, 0, 1);
		for(int i = 0; i < texts.size(); i++){
			FloatingText text = texts.get(i);
			if(text.draw(batch, font)){
				texts.remove(i);
				i--;
			}
		}
		batch.end();
		
	}
	
	@Override
	protected void end() {
	}
	
	private void renderCharacter(Entity e){
		State state = sm.get(e);
		Position pos = pm.get(e);
		Character animations = cm.get(e);
		System.out.println(state.state);
		Renderer r = animations.renderers[state.state];
		
		float angle = pos.direction.angle();
		if(angle%90 != 0){
			if(angle<90f || angle>270f){
				angle=0f;
			} else {
				angle=180f;
			}
		}
		r.direction = ((int) angle/90 + 3) % 4;
		
		
		if(dm.has(e)){
			Debuff deb = dm.get(e);
			if(deb.debuffClasses.contains(Burn.class)) batch.setColor(1, 0.5f, 0.5f, 1);
			else if(deb.debuffClasses.contains(Poison.class)) batch.setColor(0.5f, 1, 0.5f, 1);
			else if(deb.debuffClasses.contains(Push.class)){
				Push p = deb.getDebuff(Push.class);
				batch.setColor(p.r, p.g, p.b, 1);
			}
		} else batch.setColor(r.r, r.g, r.b, r.a);
		batch.draw(r.frame(world.delta), pos.x+r.ox, pos.y+r.oy);
		batch.setColor(1,1,1,1);
	}
}
