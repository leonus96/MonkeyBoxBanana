package com.iaunt.monkey.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.iaunt.monkey.Constants.PIXELS_IN_METERS;

public class BananaEntity extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public BananaEntity(World world, Texture texture, Vector2 position){
        this.world = world;
        this.texture = texture;

        // creation of body:
        BodyDef def = new BodyDef();
        def.position.set(position);
        body = world.createBody(def);

        // creation of fixture:
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("banana");

        setSize(PIXELS_IN_METERS * 1f, PIXELS_IN_METERS * 1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METERS,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METERS);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
