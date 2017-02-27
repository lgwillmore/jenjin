package com.binarymonks.jj.demo.d09;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.InstanceParams;

public class D09_spine extends Game{

    public static float WORLD_WIDTH=45;
    public static float WORLD_HEIGHT=45;

    @Override
    protected void gameOn() {
        JJ.layers.getDefaultGameLayer().setView(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

        JJ.specs.set("spineboy",spineBoy());

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("spineboy", InstanceParams.New().setPosition(WORLD_WIDTH/2,WORLD_HEIGHT/2));

        JJ.things.loadNow(scene);
    }

    public SpineSpec spineBoy(){
        SpineSpec spec = new SpineSpec();
        spec.setAtlasPath("spine/spineboy/spineboy-pma.atlas");
        spec.setData("spine/spineboy/spineboy.json", SpineSpec.DataType.JSON);
        return spec;
    }
}
