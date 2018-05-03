package com.iaunt.monkey.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import static com.iaunt.monkey.Constants.IMPULSE_JUMP;
import static com.iaunt.monkey.Constants.PIXELS_IN_METERS;
import static com.iaunt.monkey.Constants.PLAYER_SPEED;

public class MonkeyEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    private TextureRegion standRegion;
    private TextureRegion currentRegion;
    private float stateTimer;

    private Animation<TextureRegion> walkAnimation;

    private enum State {STANDING, WALKING}
    private State currentState;
    private State previousState;
    private boolean jumping;

    public MonkeyEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        // creation of textureRegions:
        standRegion = new TextureRegion(texture, 0, 0, 16, 24);
        Array<TextureRegion> walkingRegions = new Array<TextureRegion>();
        for(int i = 2; i < 6; i++)
            walkingRegions.add(new TextureRegion(texture, i*16, 0, 16, 24));
        walkAnimation = new Animation<TextureRegion>(0.1f, walkingRegions);
        walkingRegions.clear();

        // set initial state:
        currentState = State.STANDING;
        previousState = State.STANDING;
        jumping = false;
        stateTimer = 0;

        // creation of the body:
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        // creation of fixture:
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.25f, 0.5f);
        fixture = body.createFixture(shape, 1);
        shape.dispose();

        setSize(PIXELS_IN_METERS * 0.5f, PIXELS_IN_METERS * 1f);
    }

    @Override
    public void act(float delta) {
        stateTimer += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            jump();

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            currentState = State.WALKING;
        }
         else {
            currentState = State.STANDING;
        }



        if(currentState == State.STANDING) {
            // start standing:
            if(previousState != State.STANDING){
                previousState = State.STANDING;
                System.out.println("Dejé de caminar");
                stateTimer = 0;
            }
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            currentRegion = standRegion;
        }
        if(currentState == State.WALKING){
            // start walking:
            if(previousState != State.WALKING) {
                previousState = State.WALKING;
                stateTimer = 0;
            }
            body.setLinearVelocity(PLAYER_SPEED, body.getLinearVelocity().y);
            currentRegion = walkAnimation.getKeyFrame(stateTimer, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.25f) * PIXELS_IN_METERS,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METERS);
        batch.draw(currentRegion, getX(), getY(), getWidth(), getHeight());
    }

    public void jump(){
        if(jumping)
            body.applyLinearImpulse(0 , IMPULSE_JUMP, 0, 0, true);
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
