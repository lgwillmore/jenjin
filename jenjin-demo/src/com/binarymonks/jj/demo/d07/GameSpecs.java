package com.binarymonks.jj.demo.d07;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.components.ForceMovement;
import com.binarymonks.jj.components.VelocityTouchable;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.physics.FixtureBuilder;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.render.RenderBuilder;

public class GameSpecs {

    static float density = 0.3f;


    public static ThingSpec player() {

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
        spec.addComponent(new ForceMovement().setMoveForce(400).setBreakForce(400));
        return spec;
    }

    public static ThingSpec link(){
        ThingSpec spec = new ThingSpec();
        spec.setPhysics(
                new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.DynamicBody)
                        .setLinearDamping(0.0f)
        );
        spec.newNode()
                .setPhysics(
                        FixtureBuilder.New()
                                .setShape(new B2DShapeSpec.PolygonRectangle(0.5f,1f))
                                .setDensity(density)
                                .setCollisionsToExplicit(CollisionGroups.NOTHING.category,CollisionGroups.NOTHING.mask)
                                .build()
                )
                .setRender(RenderBuilder.b2d().setColor(Color.GRAY).build());
        return spec;
    }

    public static ThingSpec ball() {
        ThingSpec spec = new ThingSpec();
        spec.setPhysics(
                new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.DynamicBody)
                        .setLinearDamping(0.0f)
        );
        spec.newNode()
                .setPhysics(
                        FixtureBuilder.New()
                                .setShape(new B2DShapeSpec.Circle(2f))
                                .setDensity(0.1f)
                                .build()
                )
                .setRender(RenderBuilder.b2d().setColor(Color.GRAY).build());
        return spec;
    }


}
