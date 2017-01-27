package com.binarymonks.jj;


import com.binarymonks.jj.pools.Pools;

/**
 * The global context for controlling the framework.
 */
public class JJ {

    /**
     * API for controlling stuff to do with rendering. {@see Render}
     */
    public static Render render;

    /**
     * API for controlling stuff to do with time. {@see Time}
     */
    public static Time time = new Time();

    /**
     * API for pooling things. {@see Pools}
     */
    public static Pools pools = new Pools();


    /**
     * API for hooking into the application lifecycle.
     */
    public static LifeCycle lifecycle = new LifeCycle();

    public static Things things = new Things();

    static void initialise() {
        render = new Render();
    }

}
