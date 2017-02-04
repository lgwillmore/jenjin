package com.binarymonks.jj.backend;

import com.binarymonks.jj.layers.LayerStack;
import com.binarymonks.jj.lifecycle.LifeCyclePublisher;
import com.binarymonks.jj.physics.PhysicsWorld;
import com.binarymonks.jj.render.RenderWorld;
import com.binarymonks.jj.things.ThingWorld;
import com.binarymonks.jj.things.ThingManager;
import com.binarymonks.jj.time.TimeControls;
import com.binarymonks.jj.things.Specifications;

public class Global {
    public static RenderWorld renderWorld = new RenderWorld();
    public static TimeControls time;
    public static LifeCyclePublisher lifecycle;
    public static Specifications specs;
    public static Factories factories = new Factories();
    public static PhysicsWorld physics;
    public static ThingWorld thingWorld = new ThingWorld();
    public static ThingManager thingManager;
    public static LayerStack layerStack;
}
