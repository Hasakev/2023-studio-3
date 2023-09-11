package com.csse3200.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;

/**
 * Listens for events relevant to a Human character (Just engineers at this stage)
 * Each event will trigger a certain animation
 */
public class HumanAnimationController extends Component {
    // Event name constants
    private static final String IDLEL = "idleLeft";
    private static final String IDLER = "idleRight";
    private static final String WALKL = "walkLeftStart";
    private static final String WALKR = "walkRightStart";
    private static final String WALK_PREP = "walkPrepStart";
    private static final String PREP = "prepStart";
    private static final String FIRING_AUTO = "firingAutoStart";
    private static final String FIRING_SINGLE = "firingSingleStart";
    private static final String HIT = "hitStart";
    private static final String DEATH = "deathStart";
    // Animation name constants
    private static final String IDLEL_ANIM = "idle_left";
    private static final String IDLER_ANIM = "idle_right";
    private static final String WALKL_ANIM = "walk_left";
    private static final String WALKR_ANIM = "walk_right";
    private static final String WALK_PREP_ANIM = "walk_prep";
    private static final String FIRE_AUTO_ANIM = "firing_auto";
    private static final String FIRE_SINGLE_ANIM = "firing_single";
    private static final String HIT_ANIM = "hit";
    private static final String DEATH_ANIM = "death";
    // Sound effects constants
    private static final String FIRE_AUTO_SFX = "sounds/engineers/firing_auto.mp3";
    private static final String FIRE_SINGLE_SFX = "sounds/engineers/firing_single.mp3";

    AnimationRenderComponent animator;
    Sound fireAutoSound = ServiceLocator.getResourceService().getAsset(
            FIRE_AUTO_SFX, Sound.class);
    Sound fireSingleSound = ServiceLocator.getResourceService().getAsset(
            FIRE_SINGLE_SFX, Sound.class);

    /**
     * Instantiates a HumanAnimationController and adds all the event listeners for the
     * Human entity - Just engineers at this stage.
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener(IDLEL, this::animateIdleLeft);
        entity.getEvents().addListener(IDLER, this::animateIdleRight);
        entity.getEvents().addListener(WALKL, this::animateLeftWalk);
        entity.getEvents().addListener(WALKR, this::animateRightWalk);
        entity.getEvents().addListener(PREP, this::animatePrep);
        entity.getEvents().addListener(WALK_PREP, this::animatePrepWalk);
        entity.getEvents().addListener(FIRING_SINGLE, this::animateSingleFiring);
        entity.getEvents().addListener(FIRING_AUTO, this::animateFiring);
        entity.getEvents().addListener(HIT, this::animateHit);
        entity.getEvents().addListener(DEATH, this::animateDeath);
    }

    /**
     * Callback that starts the idle animation facing left
     */
    void animateIdleLeft() {
        animator.startAnimation(IDLEL_ANIM);
    }

    /**
     * Callback that starts the idle animation facing right
     */
    void animateIdleRight() {
        animator.startAnimation(IDLER_ANIM);
    }

    /**
     * Callback that starts the walk animation for left movement
     */
    void animateLeftWalk() {
        animator.startAnimation(WALKL_ANIM);
//        runSound.play();
    }

    /**
     * Callback that starts the walk animation for right movement
     */
    void animateRightWalk() {
        animator.startAnimation(WALKR_ANIM);
    }

    /**
     * Callback that starts the walk animation in the 'prepared' state, i.e., weapon up and ready to fight - currently
     * unused, but intended to be incorporated as engineer functionality expands
     */
    void animatePrepWalk() {
        animator.startAnimation(WALK_PREP_ANIM);
    }

    /**
     * Callback that starts the shoot animation in single fire mode, and plays the single fire sound
     */
    void animateSingleFiring() {
        animator.startAnimation(FIRE_SINGLE_ANIM);
//        fireSingleSound.play();
    }

    /**
     * Callback that starts the shoot animation in auto mode and plays the auto fire sound.
     * Currently unused, but intended to be incorporated as engineer functionality expands.
     */
    void animateFiring() {
        animator.startAnimation(FIRE_AUTO_ANIM);
//        fireAutoSound.play();
    }

    /**
     * Callback that starts the 'prep' animation, i.e., raising weapon in preparation for firing
     */
    void animatePrep() {
        animator.startAnimation(PREP);
    }

    /**
     * Callback that starts the 'hit' animation when engineer is damaged
     */
    void animateHit() {
        animator.startAnimation(HIT_ANIM);
    }

    /**
     * Callback that starts the 'death' animation when the engineer entity's health reaches zero.
     */
    void animateDeath() {
        animator.startAnimation(DEATH_ANIM);
    }
}