package com.iaunt.monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.iaunt.monkey.entities.BoxEntity;
import com.iaunt.monkey.entities.FloorEntity;
import com.iaunt.monkey.entities.MonkeyEntity;

import static com.iaunt.monkey.Constants.PIXELS_IN_METERS;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;

    private FloorEntity floor;
    private MonkeyEntity monkey;
    private BoxEntity box;

    public GameScreen(MonkeyBoxBanana game) {
        super(game);
        stage = new Stage();
        world = new World(new Vector2(0, -10), true);

        floor = new FloorEntity(world, (Texture) game.getManager().get("floor.png"), 0f, Gdx.graphics.getWidth() / PIXELS_IN_METERS, 1f);
        monkey = new MonkeyEntity(world, (Texture) game.getManager().get("monkey.png"), new Vector2(1, 2));
        box = new BoxEntity(world, (Texture) game.getManager().get("box.png"), new Vector2(2, 2));

    }

    @Override
    public void show() {
        stage.addActor(floor);
        stage.addActor(monkey);
        stage.addActor(box);
    }

    @Override
    public void hide() {
        monkey.detach();
        monkey.remove();

        box.detach();
        box.remove();

        floor.detach();
        floor.remove();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 170f/255f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
