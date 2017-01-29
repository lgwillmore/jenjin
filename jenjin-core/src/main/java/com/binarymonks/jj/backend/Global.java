package com.binarymonks.jj.backend;

import com.binarymonks.jj.lifecycle.LifeCyclePublisher;
import com.binarymonks.jj.physics.PhysicsWorld;
import com.binarymonks.jj.render.Render;
import com.binarymonks.jj.things.ThingGraphs;
import com.binarymonks.jj.things.ThingManager;
import com.binarymonks.jj.time.TimeControls;
import com.binarymonks.jj.workshop.Specifications;

public class Global {
    public static Render render;
    public static TimeControls time;
    public static LifeCyclePublisher lifecycle;
    public static Specifications specs;
    public static Factories factories = new Factories();
    public static PhysicsWorld physics;
    public static ThingGraphs thingGraphs = new ThingGraphs();
    public static ThingManager thingManager;
}
