package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	Texture vedro;
	Texture govno;
	Music normalSound;
	OrthographicCamera camera;
	SpriteBatch batch;
	Rectangle rectVedro;
	ArrayList<Rectangle> shitDrops;
	long lastTimeDrop;

	int score;

	@Override
	public void create() {

		vedro = new Texture(Gdx.files.internal("vedro.png"));
		govno = new Texture(Gdx.files.internal("KalovayaMassa.png"));
		normalSound = Gdx.audio.newMusic(Gdx.files.internal("shit.wav"));

		normalSound.setLooping(true);
		normalSound.play();

		camera = new OrthographicCamera();

		camera.setToOrtho(false, 480, 800);

		batch = new SpriteBatch();

		rectVedro = new Rectangle();

		rectVedro.x = (int)(480 / 2) - 1;
		rectVedro.y = 20;

		rectVedro.height = 64;
		rectVedro.width = 64;

		shitDrops = new ArrayList<>();
		spawnShitDrops();


	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0,0.1f,0,0);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		if (TimeUtils.nanoTime() - lastTimeDrop > 1000000000){
			
			spawnShitDrops();
			
		}

		Iterator<Rectangle> iterator = shitDrops.iterator();
		while (iterator.hasNext()){

			Rectangle shit = iterator.next();
			shit.y -= 200*Gdx.graphics.getDeltaTime();
			if (shit.y + 64 < 0){

				iterator.remove();

			}
			if (shit.overlaps(rectVedro)){

				iterator.remove();
				score += 1;

			}
		}
		batch.begin();
		batch.draw(vedro, rectVedro.x, rectVedro.y);
		for (Rectangle shits: shitDrops) {

			batch.draw(govno, shits.x, shits.y);

		}
		batch.end();

		if (Gdx.input.isTouched()){

			Vector3 vector3 = new Vector3();

			vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			camera.unproject(vector3);

			rectVedro.x = vector3.x - (int)(64/2);

		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){

			rectVedro.x -= 200*Gdx.graphics.getDeltaTime();

		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){

			rectVedro.x += 200*Gdx.graphics.getDeltaTime();

		}

	}

	public void spawnShitDrops(){

		Rectangle shitDrop = new Rectangle();
		shitDrop.y = 800;
		shitDrop.x = MathUtils.random(480-64);
		shitDrop.width = 64;
		shitDrop.height = 64;
		shitDrops.add(shitDrop);
		lastTimeDrop = TimeUtils.nanoTime();

	}

	@Override
	public void dispose() {
		govno.dispose();
		vedro.dispose();
		normalSound.dispose();
		batch.dispose();
	}

}
