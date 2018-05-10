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

    private Animation<TextureRegion> walkAnimationRight;
    private Animation<TextureRegion> walkAnimationLeft;
    private Animation<TextureRegion> climbAnimation;

    private enum State {STANDING, WALKING_RIGHT, WALKING_LEFT, CLIMB}
    private State currentState;
    private State previousState;
    private boolean jumping;

    private float newPositionX;
    private float newPositionY;
    private boolean withBox;

    private BoxEntity currentBox;
    private BoxEntity climbBox;

    public MonkeyEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        // creation of textureRegions:
        standRegion = new TextureRegion(texture, 0, 0, 16, 24);
        Array<TextureRegion> walkingRegionsRight = new Array<TextureRegion>();
        Array<TextureRegion> walkingRegionsLeft = new Array<TextureRegion>();
        Array<TextureRegion> climbRegions = new Array<TextureRegion>();
        for(int i = 2; i < 6; i++){
            walkingRegionsRight.add(new TextureRegion(texture, i*16, 0, 16, 24));
            walkingRegionsLeft.add(new TextureRegion(texture, i*16, 0, 16, 24));
            walkingRegionsLeft.get(i-2).flip(true, false);
        }

        for(int i = 7; i < 9; i++)
            climbRegions.add(new TextureRegion(texture, i*16, 0, 16, 24));

        walkAnimationRight = new Animation<TextureRegion>(0.1f, walkingRegionsRight);
        walkAnimationLeft = new Animation<TextureRegion>(0.1f, walkingRegionsLeft);
        climbAnimation = new Animation<TextureRegion>(0.1f, climbRegions);
        walkingRegionsRight.clear();
        walkingRegionsLeft.clear();
        climbRegions.clear();

        // set initial state:
        currentState = State.STANDING;
        previousState = State.STANDING;
        jumping = false;
        stateTimer = 0;
        withBox = false;

        // creation of the body:
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);
        body.setFixedRotation(true);

        // creation of fixture:
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 1f);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("monkey");
        shape.dispose();

        setSize(PIXELS_IN_METERS * 1f, PIXELS_IN_METERS * 2f);
    }

    @Override
    public void act(float delta) {
        stateTimer += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            jump();

        /*if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            currentState = State.WALKING_RIGHT;
        }
         else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            currentState = State.WALKING_LEFT;
        } else {
            currentState = State.STANDING;
        }*/

        if(currentState == State.STANDING) {
            // start standing:
            if(previousState != State.STANDING){
                previousState = State.STANDING;
                stateTimer = 0;
            }
            float vY = body.getLinearVelocity().y;
            body.setLinearVelocity(0, vY);
            currentRegion = standRegion;
        }
        if(currentState == State.WALKING_RIGHT){
            // start walking:
            if(previousState != State.WALKING_RIGHT) {
                previousState = State.WALKING_RIGHT;
                stateTimer = 0;
            }

            float vY = body.getLinearVelocity().y;
            body.setLinearVelocity(PLAYER_SPEED, vY);
            currentRegion = walkAnimationRight.getKeyFrame(stateTimer, true);

            if(body.getPosition().x >= newPositionX){
                currentState = State.STANDING;
            }
        }
        if(currentState == State.WALKING_LEFT){
            // start walking:
            if(previousState != State.WALKING_LEFT) {
                previousState = State.WALKING_LEFT;
                stateTimer = 0;
            }

            float vY = body.getLinearVelocity().y;
            body.setLinearVelocity(-PLAYER_SPEED, vY);
            currentRegion = walkAnimationLeft.getKeyFrame(stateTimer, true);

            if(body.getPosition().x <= newPositionX){
                currentState = State.STANDING;
            }
        }
        if(currentState == State.CLIMB){
            // start climb:
            if(previousState != State.CLIMB){
                previousState = State.CLIMB;
                stateTimer = 0;
            }
            float vY = body.getLinearVelocity().x;
            body.setLinearVelocity(vY, PLAYER_SPEED);
            currentRegion = climbAnimation.getKeyFrame(stateTimer, true);

            if(body.getPosition().y >= newPositionY){
                currentState = State.STANDING;
                climbBox.getBody().getFixtureList().get(0).setSensor(false);
            }

        }

        if(withBox){
            currentBox.getBody().setTransform(body.getPosition().x,
                    body.getPosition().y,
                    currentBox.getBody().getAngle());
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METERS,
                (body.getPosition().y - 1f) * PIXELS_IN_METERS);

        batch.draw(currentRegion, getX(), getY(), getWidth(), getHeight());
    }

    private void jump(){
        if(!jumping) {
            jumping = true;
            body.applyLinearImpulse(0, IMPULSE_JUMP, body.getPosition().x, body.getPosition().y, true);
        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void walkRigth(){
        currentState = State.WALKING_RIGHT;
        newPositionX = body.getPosition().x + 1f;
    }

    public void walkLeft(){
        currentState = State.WALKING_LEFT;
        newPositionX = body.getPosition().x - 1f;
    }

    public void climb(Array<BoxEntity> boxes) {
        for (int i = 0; i < boxes.size; i++) {
            System.out.println("caja " + i + ": " + boxes.get(i).getBody().getPosition().y);
            System.out.println("body: "  + body.getPosition().y);
            if ((boxes.get(i).getBody().getPosition().y < body.getPosition().y)
                    && (Math.abs(boxes.get(i).getBody().getPosition().x - body.getPosition().x) < 0.5f)) {
                currentState = State.CLIMB;
                newPositionY = body.getPosition().y + 1f;
                climbBox = boxes.get(i);
                break;
            }
        }
    }

    public void upBox(Array<BoxEntity> boxes) {
        for(int i = 0; i < boxes.size; i++) {
            if (Math.abs(boxes.get(i).getBody().getPosition().x - body.getPosition().x) < 0.5f){
                currentBox = boxes.get(i);
                withBox = true;
                break;
            }
        }

    }

    public void downBox(Array<BoxEntity> boxes) {
        float newpositionY = 1.5f;
        for(int i = 0; i < boxes.size; i++) {
            for(int j = 0; j < boxes.size; j++) {
                System.out.println("fixtureA " + boxes.get(i).getBody().getFixtureList().get(0).getUserData());
                System.out.println("fixtureB " + boxes.get(j).getBody().getFixtureList().get(0).getUserData());
                if(boxes.get(i).getBody().getFixtureList().get(0).getUserData()
                        !=
                    boxes.get(j).getBody().getFixtureList().get(0).getUserData()){
                    if(Math.abs(boxes.get(i).getBody().getPosition().x - boxes.get(j).getBody().getPosition().x) < 0.5f) {
                        newpositionY = newpositionY + 1f;
                    }
                }
            }
            if(boxes.get(i).getBody().getFixtureList().get(0).getUserData()
                    ==
                currentBox.getBody().getFixtureList().get(0).getUserData()){
                boxes.get(i).getBody().setTransform(boxes.get(i).getBody().getPosition().x, newpositionY, boxes.get(i).getBody().getAngle());
                withBox = false;
            }
        }

    }

    public boolean isJumping() {
        return jumping;
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
