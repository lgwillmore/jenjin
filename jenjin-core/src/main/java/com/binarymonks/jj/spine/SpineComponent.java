package com.binarymonks.jj.spine;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.components.Component;

public class SpineComponent extends Component {

    ObjectMap<String, SpineBoneComponent> spineBoneComponents = new ObjectMap<>();
    boolean ragDoll = false;

    @Override
    public Component clone() {
        return new SpineComponent();
    }

    @Override
    public void doWork() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public void getReady() {

    }

    public void addBone(String name, SpineBoneComponent spineBoneComponent){
        spineBoneComponents.put(name,spineBoneComponent);
        spineBoneComponent.spineParent=this;
    }

    public void triggerRagDoll() {
        if(!ragDoll) {
            ragDoll = true;
            for (ObjectMap.Entry<String, SpineBoneComponent> partEntry : spineBoneComponents) {
                partEntry.value.triggerRagDoll();
            }
        }
    }
}
