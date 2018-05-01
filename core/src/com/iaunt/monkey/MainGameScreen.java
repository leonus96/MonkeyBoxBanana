package com.iaunt.monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.iaunt.monkey.actors.MonkeyActor;

public class MainGameScreen extends BaseScreen {

    private Stage stage;

    private MonkeyActor monkeyActor;
    private Texture monkeyTexture;


    public MainGameScreen(MonkeyBoxBanana game) {
        super(game);
        monkeyTexture = new Texture("monkey.png");
    }

    @Override
    public void show() {
        stage = new Stage();

        monkeyActor = new MonkeyActor(monkeyTexture);
        stage.addActor(monkeyActor);
        monkeyActor.setPosition(20, 100);
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

    @Override
    public void dispose() {
        monkeyTexture.dispose();
    }
}
