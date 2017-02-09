package com.binarymonks.jj.time;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.pools.Poolable;

import java.util.function.Supplier;

public class Scheduler {

    int idCounter = 0;
    Supplier<Double> getTimeFunction;
    ObjectMap<Integer, ScheduledFunction> scheduledFunctions = new ObjectMap<>(200);
    Array<Integer> removals = new Array<>();

    public Scheduler(Supplier<Double> getTimeFunction) {
        this.getTimeFunction = getTimeFunction;
    }

    public void update() {
        double time = getTimeFunction.get();
        for (ObjectMap.Entry<Integer, ScheduledFunction> scheduledFunction : scheduledFunctions.entries()) {
            ScheduledFunction sf = scheduledFunction.value;
            if (sf.triggerTime <= time) {
                sf.function.call();
                if (sf.repeat) {

                } else {
                    removals.add(scheduledFunction.key);
                }
            }
        }
        for (Integer i : removals) {
            ScheduledFunction sf = scheduledFunctions.remove(i);
            JJ.pools.recycle(sf);
        }
        removals.clear();
    }

    public int scheduleInSeconds(Function function, float seconds, boolean repeat) {
        int id = idCounter++;
        scheduledFunctions.put(id, JJ.pools.nuw(ScheduledFunction.class).set(function, getTimeFunction.get() + seconds, repeat));
        return id;
    }

    public void cancelScheduled(int scheduleID) {
        scheduledFunctions.remove(scheduleID);
    }


    public static class ScheduledFunction implements Poolable {
        Function function;
        double triggerTime;
        boolean repeat;

        public ScheduledFunction set(Function function, double triggerTime, boolean repeat) {
            this.function = function;
            this.triggerTime = triggerTime;
            this.repeat = repeat;
            return this;
        }


        @Override
        public void reset() {
            function = null;
            triggerTime = 0;
            repeat = false;
        }
    }

}
