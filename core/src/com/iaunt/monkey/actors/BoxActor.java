package com.iaunt.monkey.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BoxActor extends Actor {

    private Texture boxTexture;

    public BoxActor(Texture boxTexture){
        this.boxTexture = boxTexture;
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boxTexture, getX(), getY());
    }
}
