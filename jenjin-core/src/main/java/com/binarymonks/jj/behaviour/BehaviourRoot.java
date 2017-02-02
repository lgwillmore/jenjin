package com.binarymonks.jj.behaviour;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lwillmore on 02/02/17.
 */
public class BehaviourRoot {
    ObjectMap<String, Behaviour> behaviours = new ObjectMap<>();

    public void update() {
        for (ObjectMap.Entry<String, Behaviour> behaviourEnt : behaviours) {
            behaviourEnt.value.update();
        }
    }

    public void add(Behaviour behaviour) {
        if (behaviour.name == null) {
            behaviour.name = "ANON" + behaviours.size;
        }
        behaviours.put(behaviour.name, behaviour);
    }

    public Behaviour getBehaviour(String behaviourName) {
        return behaviours.get(behaviourName);
    }

}
