package com.csse3200.game.components.player;

import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.AnimationRenderComponent;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

@ExtendWith(GameExtension.class)
class PlayerAnimationTest {
    private AutoCloseable mocks;
    private Entity entity;
    private PlayerAnimationController pac;
    private AnimationRenderComponent animator;
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/player/player.atlas"));

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);
        entity = new Entity();

        pac = spy(new PlayerAnimationController());
        animator = spy(new AnimationRenderComponent(atlas));

        pac.setEntity(entity);
        entity.addComponent(animator);
    }
    @AfterEach
    void end() throws Exception{
        mocks.close();
    }

    @Test
    void shouldCreate() {
        pac.create();
        verify(pac).create();
    }

    @Test
    void shouldStandTurn() {
        pac.create();

        pac.animateStandLeft();
        verify(pac).animateStandLeft();
        verify(animator).startAnimation("Character_StandLeft");

        pac.animateStandRight();
        verify(pac).animateStandRight();
        verify(animator).startAnimation("Character_StandRight");

        pac.animateStandUp();
        verify(pac).animateStandUp();
        verify(animator).startAnimation("Character_StandUp");
    }
    @Test
    void shouldAnimateDirections() {
        pac.create();

        pac.animateLeft();
        verify(pac).animateLeft();
        verify(animator).startAnimation("Character_Left");

        pac.animateRight();
        verify(pac).animateRight();
        verify(animator).startAnimation("Character_Right");

        pac.animateUp();
        verify(pac).animateUp();
        verify(animator).startAnimation("Character_Up");

        pac.animateDown();
        verify(pac).animateDown();
        verify(animator).startAnimation("Character_Down");

        pac.animateUpLeft();
        verify(pac).animateUpLeft();
        verify(animator).startAnimation("Character_UpLeft");

        pac.animateUpRight();
        verify(pac).animateUpRight();
        verify(animator).startAnimation("Character_UpRight");

        pac.animateDownLeft();
        verify(pac).animateDownLeft();
        verify(animator).startAnimation("Character_DownLeft");

        pac.animateDownRight();
        verify(pac).animateDownRight();
        verify(animator).startAnimation("Character_DownRight");
    }

    @Test
    void shouldAnimateStop() {
        pac.create();

        // stand left
        pac.animateStop(new Vector2(-1, 0));
        verify(pac).animateStop(new Vector2(-1, 0));
        verify(animator).startAnimation("Character_StandLeft");

        // stand right
        pac.animateStop(new Vector2(1, 0));
        verify(pac).animateStop(new Vector2(1, 0));
        verify(animator).startAnimation("Character_StandRight");

        // stand down
        pac.animateStop(new Vector2(0, -1));
        verify(pac).animateStop(new Vector2(0, -1));
        verify(animator).startAnimation("Character_StandDown");

        // stand up
        pac.animateStop(new Vector2(0, 1));
        verify(pac).animateStop(new Vector2(0, 1));
        verify(animator).startAnimation("Character_StandUp");
    }

}

