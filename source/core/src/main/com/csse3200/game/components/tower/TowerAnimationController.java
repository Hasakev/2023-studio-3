package com.csse3200.game.components.tower;

import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.AnimationRenderComponent;

/**
 * Listens for events relevant to a weapon tower state.
 * Each event will trigger a certain animation
 */
public class TowerAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("idleStart", this::animateIdle);
        entity.getEvents().addListener("stowStart", this::animateStow);
        entity.getEvents().addListener("deployStart", this::animateDeploy);
        entity.getEvents().addListener("firingStart", this::animateFiring);

    }

    void animateIdle() {
        animator.startAnimation("idle");
    }

    void animateStow() {
        animator.startAnimation("stow");
    }

    void animateDeploy() {
        animator.startAnimation("deploy");
    }

    void animateFiring() {
        animator.startAnimation("firing");
    }
}