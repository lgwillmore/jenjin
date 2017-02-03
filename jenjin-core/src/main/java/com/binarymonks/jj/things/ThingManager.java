package com.binarymonks.jj.things;

import com.binarymonks.jj.JJ;
import com.binarymonks.jj.api.Things;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.things.specs.SceneSpec;

import java.util.function.Consumer;

public class ThingManager implements Things {

    SceneLoader sceneLoader = new SceneLoader();

    public ThingManager() {
        JJ.pools.registerManager(new CreateThingPoolManager(), CreateThingFunction.class);
    }

    @Override
    public Thing getThingByName(String uniqueName) {
        return Global.thingWorld.getThingByUniqueName(uniqueName);
    }

    @Override
    public void load(SceneSpec sceneSpec, Function callback) {
        sceneLoader.load(sceneSpec);
        callback.call();
    }

    @Override
    public void load(SceneSpec sceneSpec) {
        sceneLoader.load(sceneSpec);
    }

    @Override
    public void create(String thingSpecPath, InstanceParams instanceParams, Consumer<Thing> callback) {
        if (!Global.physics.isUpdating()) {
            Thing thing = Global.factories.things.create(thingSpecPath, instanceParams);
            callback.accept(thing);
        } else {
            CreateThingFunction delayedCreate = N.ew(CreateThingFunction.class);
            delayedCreate.set(thingSpecPath, instanceParams, callback);
            Global.physics.addPostPhysicsFunction(delayedCreate);
        }
    }

    @Override
    public void create(String thingSpecPath, InstanceParams instanceParams) {
        create(thingSpecPath, instanceParams, this::doNothingCallback);
    }

    void doNothingCallback(Thing thing) {
        //user did not want the object created.
    }

    public static class CreateThingFunction implements Function {
        String thingSpecPath;
        InstanceParams instanceParams;
        Consumer<Thing> callback;

        public void set(String thingSpecPath, InstanceParams instanceParams, Consumer<Thing> callback) {
            this.thingSpecPath = thingSpecPath;
            this.instanceParams = instanceParams;
            this.callback = callback;
        }

        @Override
        public void call() {
            Thing thing = Global.factories.things.create(thingSpecPath, instanceParams);
            callback.accept(thing);
            Re.cycle(this);
        }
    }

    public static class CreateThingPoolManager implements PoolManager<CreateThingFunction> {

        @Override
        public void reset(CreateThingFunction createThingFunction) {
            createThingFunction.callback = null;
            createThingFunction.instanceParams = null;
            createThingFunction.thingSpecPath = null;
        }

        @Override
        public CreateThingFunction create_new() {
            return new CreateThingFunction();
        }

        @Override
        public void dispose(CreateThingFunction createThingFunction) {

        }
    }
}
