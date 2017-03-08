package com.binarymonks.jj.demo.d09;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.spine.SpineSkeletonSpec;
import com.binarymonks.jj.things.InstanceParams;

public class D09_spine extends Game {

    public static float WORLD_WIDTH = 10;
    public static float WORLD_HEIGHT = 10;

    public D09_spine(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {
        JJ.layers.getDefaultGameLayer().setView(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

        JJ.specs.set("spineboy", spineBoy());

        SceneSpec scene = new SceneSpec();
        scene.addInstance("spineboy", InstanceParams.New().setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2));

        JJ.things.loadNow(scene);
    }

    public SpineSpec spineBoy() {
        SpineSpec spec = new SpineSpec();
        spec.setAtlasPath("spine/spineboy/spineboy-pma.atlas");
        spec.setData("spine/spineboy/spineboy.json", SpineSpec.DataType.JSON);
        spec.setOrigin(0, 247f);
        spec.setScale(1 / 247f);
        spec.setStartingAnimation("walk");
        spec.setSkeletonSpec(new SpineSkeletonSpec()
                .setBoneOverride(
                        "head",
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.Circle(0.4f))
                                .setOffset(0.4f, 0)
                )
        );
        return spec;
    }
}
