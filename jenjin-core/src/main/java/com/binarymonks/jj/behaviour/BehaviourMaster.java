package com.binarymonks.jj.behaviour;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lwillmore on 10/02/17.
 */
public class BehaviourMaster {

    ObjectMap<Class<Behaviour>, Behaviour> trackedBehaviours = new ObjectMap<>();
    ObjectMap<Class<Behaviour>, Behaviour> addTrackedBehaviours = new ObjectMap<>();
    ObjectMap<Class<Behaviour>, Behaviour> removetrackedBehaviours = new ObjectMap<>();

    public void update() {
        clean();
        for (ObjectMap.Entry<Class<Behaviour>, Behaviour> entry : trackedBehaviours.entries()) {
            if (entry.value.isDone()) {
                removetrackedBehaviours.put(entry.key, entry.value);
            } else {
                entry.value.doWork();
            }
        }
    }

    public void clean() {
        for (ObjectMap.Entry<Class<Behaviour>, Behaviour> entry : removetrackedBehaviours.entries()) {
            entry.value.tearDown();
            trackedBehaviours.remove(entry.key);
        }
        removetrackedBehaviours.clear();
        for (ObjectMap.Entry<Class<Behaviour>, Behaviour> entry : addTrackedBehaviours.entries()) {
            trackedBehaviours.put(entry.key, entry.value);
            entry.value.getReady();
        }
        addTrackedBehaviours.clear();
    }

    public void neutralise() {
        clean();
        for (ObjectMap.Entry<Class<Behaviour>, Behaviour> entry : trackedBehaviours.entries()) {
            entry.value.tearDown();
        }
    }

    public void reactivate() {
        for (ObjectMap.Entry<Class<Behaviour>, Behaviour> entry : trackedBehaviours.entries()) {
            entry.value.getReady();
        }
    }
    
    public void addBehavior(Behaviour behaviour) {
        if (!behaviour.type().isAssignableFrom(behaviour.getClass())) {
            throw new RuntimeException(String.format(
                    "Your behaviour %s is not an instance of its type %s",
                    behaviour.getClass().getCanonicalName(),
                    behaviour.type().getCanonicalName()
            ));
        }
        if (trackedBehaviours.containsKey(behaviour.type()) || addTrackedBehaviours.containsKey(behaviour.type())) {
            throw new RuntimeException(String.format(
                    "Your are adding a tracked behaviour that will clobber another behaviour of type %s",
                    behaviour.type().getCanonicalName()
            ));
        }
        addTrackedBehaviours.put(behaviour.type(), behaviour);
    }

    public <T extends Behaviour> T getTrackedBehaviour(Class<Behaviour> type) {
        if (trackedBehaviours.containsKey(type)) {
            return (T) trackedBehaviours.get(type);
        }
        if (addTrackedBehaviours.containsKey(type)) {
            return (T) addTrackedBehaviours.get(type);
        }
        return null;
    }

}
