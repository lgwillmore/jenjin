package com.binarymonks.jj.time;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.async.Function;

import java.util.function.Supplier;

public class Scheduler {

    int idCounter = 0;
    Supplier<Double> getTimeFunction;
    ObjectMap<Integer, ScheduledFuncion> scheduledFunctions = new ObjectMap<>(200);
    Array<Integer> removals = new Array<>();

    public Scheduler(Supplier<Double> getTimeFunction) {
        this.getTimeFunction = getTimeFunction;
    }

    public void update() {
        double time = getTimeFunction.get();
        for (ObjectMap.Entry<Integer, ScheduledFuncion> scheduledFunction : scheduledFunctions.entries()) {
            if (scheduledFunction.value.triggerTime <= time) {
                scheduledFunction.value.function.call();
                removals.add(scheduledFunction.key);
            }
        }
        for (Integer i : removals) {
            scheduledFunctions.remove(i);
        }
        removals.clear();
    }

    public int scheduleInSeconds(Function function, float seconds) {
        int id = idCounter++;
        scheduledFunctions.put(id, new ScheduledFuncion(function, getTimeFunction.get() + seconds));
        return id;
    }

    public void cancelScheduled(int scheduleID) {
        scheduledFunctions.remove(scheduleID);
    }


    public static class ScheduledFuncion {
        Function function;
        double triggerTime;

        public ScheduledFuncion(Function function, double triggerTime) {
            this.function = function;
            this.triggerTime = triggerTime;
        }
    }

}
