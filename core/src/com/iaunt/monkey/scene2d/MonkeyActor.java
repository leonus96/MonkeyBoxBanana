package com.iaunt.monkey.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class MonkeyActor extends Actor{

    private Texture monkeyTexture;

    private TextureRegion monkeyStand;

    private TextureRegion currentFrame;


    private enum State {STANDING, WALKING};
    private State currentSatate;

    private Animation<TextureRegion> monkeyWalk;
    private boolean walkingRight;
    private float stateTimer;

    public MonkeyActor(Texture monkeyTexture) {
        this.monkeyTexture = monkeyTexture;
        currentSatate = State.STANDING;
        stateTimer = 0;
        walkingRight = true;

        monkeyStand = new TextureRegion(getTexture(), 0, 0, 16, 24);
        setSize(monkeyStand.getRegionWidth(), monkeyStand.getRegionHeight());

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i  = 2; i <6; i++)
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 24 ));
        monkeyWalk = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    @Override
    public void act(float delta) {
        stateTimer += delta;
        setCurrentFrame(monkeyWalk.getKeyFrame(stateTimer, true));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getCurrentFrame(), getX(), getY());
    }



    private  Texture getTexture() {
        return this.monkeyTexture;
    }

    private void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    private TextureRegion getCurrentFrame() {
        return this.currentFrame;
    }


}
