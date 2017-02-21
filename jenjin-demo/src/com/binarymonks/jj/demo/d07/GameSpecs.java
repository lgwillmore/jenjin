package com.binarymonks.jj.demo.d07;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.physics.FixtureBuilder;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.render.RenderBuilder;

/**
 * Created by lwillmore on 20/02/17.
 */
public class GameSpecs {


    public static ThingSpec player() {
        float density = 0.5f;
        ThingSpec spec = new ThingSpec();
        spec.setPhysics(
                new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.DynamicBody)
        );
        spec.newNode()
                .setPhysics(
                        new FixtureBuilder()
                                .setShape(new B2DShapeSpec.Circle(5))
                                .setDensity(density)
                                .build()
                )
                .setRender(RenderBuilder.b2d().setColor(Color.BLUE).build())
        ;
        return spec;
    }


}
