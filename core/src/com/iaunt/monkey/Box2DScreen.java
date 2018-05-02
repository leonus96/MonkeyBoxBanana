package com.iaunt.monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body monkeyBody, floorBody;
    private Fixture monkeyFixture;

    public Box2DScreen(MonkeyBoxBanana game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(32, 18);

        BodyDef monkeyDef = createMonkeyBodyDef();
        monkeyBody = world.createBody(monkeyDef);

        PolygonShape monkeyShape = new PolygonShape();
        monkeyShape.setAsBox(1, 1);
        monkeyFixture = monkeyBody.createFixture(monkeyShape, 1);
        monkeyShape.dispose();
    }

    private BodyDef createMonkeyBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, 10);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    @Override
    public void dispose() {
        world.destroyBody(monkeyBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);
    }


}
