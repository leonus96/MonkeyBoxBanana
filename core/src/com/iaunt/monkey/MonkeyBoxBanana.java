package com.iaunt.monkey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MonkeyBoxBanana extends Game {

	private AssetManager manager;
	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("monkey.png", Texture.class);
		manager.load("monkey1.png", Texture.class);
		manager.load("box.png", Texture.class);
		manager.load("floor.png", Texture.class);
		manager.finishLoading();

		setScreen(new GameScreen(this));
	}

	public AssetManager getManager() {
		return manager;
	}
}
