package com.soc.screens;

import javax.swing.GroupLayout.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
		this.devsBody = "Aritz Bilbao Jayo @aritzbi\n"
				+ "Aimar Rodriguez Soto @aimarrodr";
		this.graphics = "Art and Animation";
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
		this.testersBody = "Joseba Rojo @PitilinFutxinJo\n"
				+ "Peio Iñurrigarro\n"
				+ "Jesus Semsa @Alchemy_Meister\n"
				+ "Iban Eguia @Razican\n"
				+ "Anaïs Galván @aanais\n"
				+ "Aitor Brazaola @kronoshz\n"
				+ "Ariane Lazaga @NancyCallahan88\n"
				+ "Jon Lorente @jonlorente";
		this.cam = SoC.game.camera;
		this.batch = super.getBatch();
		this.font = new Skin(Gdx.files.internal("resources/skin2.json" )).getFont("menuFont");
		
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
	       
	       if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		       cam.position.y -= 450*delta;
	       } else {
	    	   cam.position.y -= 50*delta;
	       }
	       cam.update();
	       
	       System.out.println(cam.position.y);
	       
	       font.setColor(1,1,1,1);
	       batch.setColor(1,1,1,1);
	       
	       batch.begin();
	       batch.setProjectionMatrix(cam.combined);
	       	       
	       font.setScale(4f);
	       font.drawWrapped(batch, title, 100, 4900, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(2f);
	       font.drawWrapped(batch, devs, 100, 4700, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(1f);
	       font.drawWrapped(batch, devsBody, 100, 4620, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(2f);
	       font.drawWrapped(batch, graphics, 100, 4450, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(1f);
	       font.drawWrapped(batch, graphicsBody, 100, 4370, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(2f);
	       font.drawWrapped(batch, audio, 100, 4000, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(1f);
	       font.drawWrapped(batch, audioBody, 100, 3920, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(2f);
	       font.drawWrapped(batch, specialThanks, 100, 3600, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(1.5f);
	       font.drawWrapped(batch, testers, 100, 3450, width - 100, BitmapFont.HAlignment.CENTER);
	       font.setScale(1f);
	       font.drawWrapped(batch, testersBody, 100, 3380, width - 100, BitmapFont.HAlignment.CENTER);
	       
	       
	       
	       batch.end();	       
	       
	       font.setScale(1);

	}

}
