package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;

public class AnimationProvider implements TextureProvider {

    Disposable backingTexture;
    double startTime = 0;
    ObjectMap<String, Animation> animations = new ObjectMap<>();
    Animation currentAnimation;

    public AnimationProvider(Disposable backingTexture, ObjectMap<String, Animation> animations) {
        this.backingTexture = backingTexture;
        this.animations = animations;
        triggerAnimation("default", Animation.PlayMode.LOOP);
    }

    @Override
    public TextureRegion getFrame(float rotationD) {
        float elapsedTime = (float) (JJ.time.getTime() - startTime);
        if (currentAnimation == null) {
            return null;
        }
        if (currentAnimation.getPlayMode() != Animation.PlayMode.LOOP && currentAnimation.isAnimationFinished(elapsedTime)) {
            currentAnimation = null;
            return null;
        }
        return currentAnimation.getKeyFrame(elapsedTime);
    }

    public void triggerAnimation(String name, Animation.PlayMode playMode) {
        startTime = JJ.time.getTime();
        currentAnimation = animations.get(name);
        currentAnimation.setPlayMode(playMode);
    }

    @Override
    public void dispose() {
        backingTexture.dispose();
    }
}
