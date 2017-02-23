package com.binarymonks.jj.demo.d07;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.components.ImpulseMovement;
import com.binarymonks.jj.components.ImpulseTouchable;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.physics.FixtureBuilder;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.render.RenderBuilder;

public class GameSpecs {


    public static ThingSpec player() {
        float density = 0.5f;
        ThingSpec spec = new ThingSpec();
        spec.setPhysics(
                new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.DynamicBody)
                        .setLinearDamping(0.0f)
        );
        spec.newNode()
                .setPhysics(
                        FixtureBuilder.New()
                                .setShape(new B2DShapeSpec.Circle(2.5f))
                                .setDensity(density)
                                .build()
                )
                .setRender(RenderBuilder.b2d().setColor(Color.BLUE).build());
        spec.addComponent(new ImpulseMovement());
        spec.addComponent(new ImpulseTouchable().setMovementForce(8).setBreakingForce(3));
        return spec;
    }


}
