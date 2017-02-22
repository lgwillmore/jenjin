package com.binarymonks.jj.pools;

import com.binarymonks.jj.JJ;

public class Re {

    public static void cycle(Object... objects) {
        for (Object o : objects) {
            JJ.pools.recycle(o);
        }
    }

    public static void cycleItems(Iterable<?> collectionOfObjects) {
        for (Object o : collectionOfObjects) {
            cycle(o);
        }
    }
}
