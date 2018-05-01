package com.iaunt.monkey;

import com.badlogic.gdx.Game;

public class MonkeyBoxBanana extends Game {

	@Override
	public void create() {
		setScreen(new MainGameScreen(this));
	}
}
