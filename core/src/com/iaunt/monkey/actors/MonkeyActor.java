package com.iaunt.monkey.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MonkeyActor extends Actor{

    private Texture monkeyTexture;

    public MonkeyActor(Texture monkeyTexture) {
            this.monkeyTexture = monkeyTexture;
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(monkeyTexture, getX(), getY());
    }
}
