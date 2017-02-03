package com.binarymonks.jj.behaviour;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lwillmore on 02/02/17.
 */
public class BehaviourRoot {
    ObjectMap<Class<?>, Behaviour> behaviours = new ObjectMap<>();

    public void update() {
        for (ObjectMap.Entry<Class<?>, Behaviour> behaviourEnt : behaviours) {
            behaviourEnt.value.update();
        }
    }

    public void add(Behaviour behaviour) {
        if(!behaviour.type().isAssignableFrom(behaviour.getClass())){
            throw new InvalidTypeInheritanceException(behaviour.getClass(),behaviour.type());
        }
        behaviours.put(behaviour.type(), behaviour);
    }

    public <T extends Behaviour> T get(Class<T> type) {
        return (T) behaviours.get(type);
    }

    public static class InvalidTypeInheritanceException extends RuntimeException {
        public InvalidTypeInheritanceException(Class<?> typeAdded, Class<?> returnedByType) {
            super(String.format(
                    "You are trying to add a behaviour with class %s, which is not a subtype of its type %s",
                    typeAdded.getCanonicalName(),
                    returnedByType.getCanonicalName())
            );
        }
    }

}
