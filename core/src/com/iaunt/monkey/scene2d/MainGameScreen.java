package com.iaunt.monkey.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.iaunt.monkey.BaseScreen;
import com.iaunt.monkey.MonkeyBoxBanana;

public class MainGameScreen extends BaseScreen {

    private Stage stage;

    private MonkeyActor monkeyActor;
    private Texture monkeyTexture;

    private BoxActor boxActor;
    private Texture boxTexture;


    public MainGameScreen(MonkeyBoxBanana game) {
        super(game);

        monkeyTexture = new Texture("monkey1.png");

        boxTexture = new Texture("box.png");
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);

        monkeyActor = new MonkeyActor(monkeyTexture);
        stage.addActor(monkeyActor);
        monkeyActor.setPosition(20, 100);

        boxActor = new BoxActor(boxTexture);
        stage.addActor(boxActor);
        boxActor.setPosition(40, 100);
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
