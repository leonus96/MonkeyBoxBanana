package com.iaunt.monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainGameScreen extends BaseScreen {

    private Stage stage;

    public MainGameScreen(MonkeyBoxBanana game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla:
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Stage:
        stage.act(); //actualiza
        stage.draw(); // dibuja
    }


}
