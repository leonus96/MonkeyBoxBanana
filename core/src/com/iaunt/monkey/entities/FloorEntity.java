package com.iaunt.monkey.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.org.apache.xml.internal.utils.SystemIDResolver;

import javax.xml.soap.Text;

import static com.iaunt.monkey.Constants.PIXELS_IN_METERS;

public class FloorEntity extends Actor {

    private Texture texture;
    private TextureRegion textureRegion;
    private World world;
    private Body body;
    private Fixture fixture;

    public FloorEntity(World world, Texture texture, float x, float width, float y){
        this.world = world;
        this.texture = texture;

        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        textureRegion = new TextureRegion(texture, 0, 0, Gdx.graphics.getWidth()/4, texture.getHeight());


        // creation of body:
        BodyDef def = new BodyDef();
        def.position.set(x + width/2, y - 0.5f);
        body = world.createBody(def);

        // creation  of fixture:
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, 0.5f);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("floor");
        shape.dispose();

        setSize(PIXELS_IN_METERS * width, PIXELS_IN_METERS);
        setPosition((body.getPosition().x - width / 2) * PIXELS_IN_METERS,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METERS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
