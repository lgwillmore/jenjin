package com.binarymonks.jj.things;

import com.binarymonks.jj.JJ;
import com.binarymonks.jj.api.Things;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.async.FunctionLink;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.specs.SceneNodeSpec;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;

import java.util.function.Consumer;

public class ThingManager implements Things {


    public ThingManager() {
        JJ.pools.registerManager(new CreateThingPoolManager(), CreateThingFunction.class);
    }

    private Thing nowThing;

    @Override
    public Thing getThingByName(String uniqueName) {
        return Global.thingWorld.getThingByUniqueName(uniqueName);
    }

    @Override
    public void load(SceneSpec sceneSpec, Function callback) {
        JJ.specs.loadSpecAssetsThen(
                FunctionLink.New()
                        .Do(() -> Global.factories.scenes.create(sceneSpec, InstanceParams.New()))
                        .thenDo(callback)
        );
    }

    @Override
    public void loadNow(SceneSpec sceneSpec) {
        JJ.specs.loadSpecAssetsNow();
        Global.factories.scenes.create(sceneSpec, InstanceParams.New());
    }


    @Override
    public void create(String thingSpecPath, InstanceParams instanceParams, Consumer<Thing> callback) {
        CreateThingFunction delayedCreate = N.ew(CreateThingFunction.class);
        delayedCreate.set(thingSpecPath, instanceParams, callback);
        if (!Global.physics.isUpdating()) {
            delayedCreate.call();
        } else {
            Global.physics.addPostPhysicsFunction(delayedCreate);
        }
    }

    @Override
    public void create(String thingSpecPath, InstanceParams instanceParams) {
        create(thingSpecPath, instanceParams, this::doNothingCallback);
    }

    @Override
    public Thing createNow(String thingSpecPath, InstanceParams instanceParams) {
        nowThing = null;
        create(thingSpecPath, instanceParams, this::getNowThing);
        return nowThing;
    }

    private void getNowThing(Thing thing) {
        nowThing = thing;
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
            SceneNodeSpec spec = Global.specs.specifications.get(thingSpecPath);
            Thing thing = null;
            if (spec instanceof ThingSpec) {
                ThingSpec thingSpec = (ThingSpec) spec;
                thing = Global.factories.things.create(thingSpec, instanceParams);
            }
            if (spec instanceof SceneSpec) {
                SceneSpec sceneSpec = (SceneSpec) spec;
                Global.factories.scenes.create(sceneSpec,instanceParams);
            }
            if (spec instanceof SpineSpec){
                SpineSpec spineSpec = (SpineSpec) spec;
                thing = Global.factories.spine.create(spineSpec,instanceParams);
            }
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
