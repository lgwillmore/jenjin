package com.binarymonks.jj;


import com.binarymonks.jj.api.*;
import com.binarymonks.jj.assets.AssetManager;
import com.binarymonks.jj.async.LoopTasks;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.events.EventBus;
import com.binarymonks.jj.input.BaseInputProcessor;
import com.binarymonks.jj.layers.LayerStack;
import com.binarymonks.jj.api.Layers;
import com.binarymonks.jj.lifecycle.LifeCyclePublisher;
import com.binarymonks.jj.physics.PhysicsWorld;
import com.binarymonks.jj.pools.Pools;
import com.binarymonks.jj.render.RenderWorld;
import com.binarymonks.jj.things.ThingManager;
import com.binarymonks.jj.time.TimeControls;
import com.binarymonks.jj.things.Specifications;

/**
 * The Jenjin API.
 */
public class JJ {

    /**
     * API for getting time delta and controlling the flow of time. {@see TimeControls}
     */
    public static Time time;

    /**
     * API for pooling things. {@see Pools}
     */
    public static Pools pools = new Pools();


    /**
     * API for hooking into the application lifecycle.
     */
    public static LifeCycle lifecycle;

    public static Things things;

    public static Specs specs;

    public static Layers layers;

    public static Physics physics;

    public static Assets assets;

    public static Audio audio = new Audio();

    public static Tasks tasks;

    public static EventBus events = new EventBus();
    public static JJInput input;
    public static Lights lights;

    static void initialise(JJConfig jjConfig) {
        Global.config=jjConfig;

        TimeControls timeControls = new TimeControls();
        Global.time = timeControls;
        time = timeControls;

        LifeCyclePublisher lifeCyclePublisher = new LifeCyclePublisher();
        Global.lifecycle = lifeCyclePublisher;
        lifecycle = lifeCyclePublisher;

        BaseInputProcessor baseInputProcessor = new BaseInputProcessor();
        input = baseInputProcessor;
        Global.input = baseInputProcessor;

        LayerStack layerStack = new LayerStack(baseInputProcessor);
        layers = layerStack;
        Global.layerStack = layerStack;

        Specifications specifications = new Specifications();
        Global.specs = specifications;
        specs = specifications;

        ThingManager thingManager = new ThingManager();
        Global.thingManager = thingManager;
        things = thingManager;

        PhysicsWorld physicsWorld = new PhysicsWorld();
        Global.physics = physicsWorld;
        physics = physicsWorld;

        LoopTasks loopTasks = new LoopTasks();
        Global.tasks = loopTasks;
        tasks = loopTasks;

        assets = new AssetManager();

        RenderWorld renderWorld = new RenderWorld();
        Global.renderWorld = renderWorld;
        lights=renderWorld;

    }

}
