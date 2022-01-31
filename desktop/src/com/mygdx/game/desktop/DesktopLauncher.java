package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGame(), configuration);

		configuration.title = "Где моё ведро Говна?";
		configuration.width = 480;
		configuration.height = 800;



	}
}
