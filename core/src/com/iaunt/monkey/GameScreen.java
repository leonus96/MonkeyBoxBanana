package com.iaunt.monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.iaunt.monkey.entities.BananaEntity;
import com.iaunt.monkey.entities.BoxEntity;
import com.iaunt.monkey.entities.FloorEntity;
import com.iaunt.monkey.entities.MonkeyEntity;

import static com.iaunt.monkey.Constants.PIXELS_IN_METERS;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private FloorEntity floor;
    private MonkeyEntity monkey;
    private Array<BoxEntity> boxes = new Array<BoxEntity>();
    private BananaEntity banana;

    private Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;

    public GameScreen(MonkeyBoxBanana game) {
        super(game);
        stage = new Stage();
        world = new World(new Vector2(0, -10), true);

        debugRenderer =  new Box2DDebugRenderer();
        camera = new OrthographicCamera(16, 9);
        camera.translate(6,0);

        world.setContactListener(new ContactListener() {
            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if (areCollided(contact, "monkey", "floor")) {
                    monkey.setJumping(false);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });





    }

    @Override
    public void show() {
        floor = new FloorEntity(world, (Texture) game.getManager().get("floor.png"), 0f, Gdx.graphics.getWidth() / PIXELS_IN_METERS, 1f);
        System.out.println(Gdx.graphics.getWidth() );
        monkey = new MonkeyEntity(world, (Texture) game.getManager().get("monkey1.png"), new Vector2(6.5f, 2));
        boxes.add(new BoxEntity(world, (Texture) game.getManager().get("box.png"), new Vector2(1.5f, 1.5f), "box1"));
        boxes.add(new BoxEntity(world, (Texture) game.getManager().get("box.png"), new Vector2(11.5f, 1.5f), "box2"));
        banana = new BananaEntity(world, (Texture) game.getManager().get("banana.png"), new Vector2(6.5f, 8));

        stage.addActor(floor);
        stage.addActor(monkey);
        stage.addActor(boxes.get(0));
        stage.addActor(boxes.get(1));
        stage.addActor(banana);
    }

    @Override
    public void hide() {
        monkey.detach();
        monkey.remove();

        boxes.get(0).detach();
        boxes.get(0).remove();

        boxes.get(1).detach();
        boxes.get(1).remove();

        floor.detach();
        floor.remove();

        banana.detach();
        banana.remove();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 170f/255f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            monkey.walkRigth();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            monkey.walkLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            monkey.upBox(boxes);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            monkey.downBox(boxes);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            monkey.climb(boxes);
        }

        stage.act();

        world.step(delta, 6, 2);
        camera.update();
        debugRenderer.render(world, camera.combined);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
