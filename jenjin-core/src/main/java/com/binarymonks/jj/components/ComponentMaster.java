package com.binarymonks.jj.components;

import com.badlogic.gdx.utils.ObjectMap;

public class ComponentMaster {

    ObjectMap<Class<Component>, Component> trackedComponents = new ObjectMap<>();
    ObjectMap<Class<Component>, Component> addTrackedComponent = new ObjectMap<>();
    ObjectMap<Class<Component>, Component> removeTrackedComponents = new ObjectMap<>();

    public void update() {
        clean();
        for (ObjectMap.Entry<Class<Component>, Component> entry : trackedComponents.entries()) {
            if (entry.value.isDone()) {
                removeTrackedComponents.put(entry.key, entry.value);
            } else {
                entry.value.doWork();
            }
        }
    }

    public void clean() {
        for (ObjectMap.Entry<Class<Component>, Component> entry : removeTrackedComponents.entries()) {
            entry.value.tearDown();
            trackedComponents.remove(entry.key);
        }
        removeTrackedComponents.clear();
        for (ObjectMap.Entry<Class<Component>, Component> entry : addTrackedComponent.entries()) {
            trackedComponents.put(entry.key, entry.value);
            entry.value.getReady();
        }
        addTrackedComponent.clear();
    }

    public void neutralise() {
        clean();
        for (ObjectMap.Entry<Class<Component>, Component> entry : trackedComponents.entries()) {
            entry.value.tearDown();
        }
    }

    public void reactivate() {
        for (ObjectMap.Entry<Class<Component>, Component> entry : trackedComponents.entries()) {
            entry.value.getReady();
        }
    }

    public void addComponent(Component component) {
        if (!component.type().isAssignableFrom(component.getClass())) {
            throw new RuntimeException(String.format(
                    "Your component %s is not an instance of its type %s",
                    component.getClass().getCanonicalName(),
                    component.type().getCanonicalName()
            ));
        }
        if (trackedComponents.containsKey(component.type()) || addTrackedComponent.containsKey(component.type())) {
            throw new RuntimeException(String.format(
                    "Your are adding a tracked component that will clobber another component of type %s",
                    component.type().getCanonicalName()
            ));
        }
        addTrackedComponent.put(component.type(), component);
    }

    public <T extends Component> T getTrackedComponent(Class<Component> type) {
        if (trackedComponents.containsKey(type)) {
            return (T) trackedComponents.get(type);
        }
        if (addTrackedComponent.containsKey(type)) {
            return (T) addTrackedComponent.get(type);
        }
        return null;
    }

}
