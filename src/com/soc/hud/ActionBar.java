package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.soc.core.SoC;

public class ActionBar extends Actor implements InputProcessor{

	public Texture slot;
	public HudSystem parent;
	int[] spells;
	int tooltip;
	
	public ActionBar(HudSystem parent){
		slot = new Texture(Gdx.files.internal("resources/spell-container.png"));
		this.parent = parent;
		parent.stage.addActor(this);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		spells = SoC.game.statsmapper.get(SoC.game.player).spells;
		for(int i = 0; i < spells.length; i++){
			batch.draw(SoC.game.spells[spells[i]].icon, getX()+(i*slot.getWidth()+5), getY()+7);
			//batch.draw(slot, getX()+(i*slot.getWidth()+2), getY());
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Vector2 coords = parent.stage.stageToScreenCoordinates(new Vector2(screenX, screenY));
		spells = SoC.game.statsmapper.get(SoC.game.player).spells;
		if(coords.x > getX() && coords.x < getX() + (slot.getWidth()+2)*spells.length && coords.y > getY() && coords.y < getY() + 64){
			for(int i = 0; i < spells.length; i++){
				if(coords.x > getX()+(slot.getWidth()+2)*(i) && coords.x < (slot.getWidth()+2)*(i+1) && coords.y > getY() && coords.y < getY() + 64){
					parent.tooltip.setText(SoC.game.spells[spells[i]].tooltip, 0.2f);
					return false;
				}
			}
		}
		parent.tooltip.setText(null, 0);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
