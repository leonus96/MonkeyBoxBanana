package com.iaunt.monkey.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MonkeyActor extends Actor{

    private TextureRegion monkeyTexture;

    public MonkeyActor(TextureRegion monkeyTexture) {
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
