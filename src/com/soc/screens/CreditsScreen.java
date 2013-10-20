package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.soc.core.SoC;
import com.soc.utils.GraphicsLoader;

public class CreditsScreen extends AbstractScreen{

	public String title;
	public String devs;
	public String devsBody;
	public String graphics;
	public String graphicsBody;
	public String audio;
	public String audioBody;
	public String specialThanks;
	public String testers;
	public String testersBody;
	public OrthographicCamera cam;
	public BitmapFont font;
	public SpriteBatch batch;
	public float width;
	
	public CreditsScreen(SoC game) {
		super(game);
		this.title = "Sons of Cydonia";
		this.devs = "Design and Programming";
		this.devsBody = "Aritz Bilbao Jayo @aritzbi\n\n"
				+ "Aimar Rodriguez Soto @aimarrodr";
		this.graphics = "Art";
		this.graphicsBody = "Johannes Sjolund\n"
				+ "Johann Charlot\n"
				+ "Daniel Eddeland\n"
				+ "Artur Reterski\n"
				+ "Dethe Elza\n"
				+ "Skyler Robert Colladay\n"
				+ "Casper Nilsson\n"
				+ "Joe White\n"
				+ "";
		this.audio = "Music and Sound effects";
		this.audioBody = "Alexandr Zelanhov\n"
				+ "Johan Brood\n"
				+ "Matthew Pablo\n"
				+ "Jordan Trudgett\n"
				+ "Gobusto";
		this.specialThanks = "Special Thanks";
		this.testers = "Testers";
		this.testersBody = "";
		this.cam = SoC.game.camera;
		this.batch = super.getBatch();
		this.font = new Skin(Gdx.files.internal("resources/skin2.json" )).getFont("gameFont");
		
		cam.position.set(0,5000,0);
	}
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		float y = cam.position.y;
		cam.setToOrtho(false, width, height);
		cam.position.y = y;
	}
	
	public void render(float delta) {
	       Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	       Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	       
	       cam.position.y -= 100*delta;
	       cam.update();
	       
	       System.out.println(cam.position.y);
	       
	       font.setColor(1,1,1,1);
	       batch.setColor(1,1,1,1);
	       
	       batch.begin();
	       batch.setProjectionMatrix(cam.combined);
	       
	     //  float fontX = buttonX + buttonWidth/2 - font.getBounds(fontText).width/2;
	      // float fontY = buttonY + buttonHeight/2 + font.getBounds(fontText).height/2;
	       
	       font.drawWrapped(batch, title, width/2-width/4, 4900, width/2);
	       font.drawWrapped(batch, devs, width/2-width/4, 4700, width/2);
	       font.setScale(0.8f);
	       font.drawWrapped(batch, devsBody, width/2-width/4.5f, 4600, width/2);
	       batch.end();	       
	       
	       font.setScale(1);

	}

}
