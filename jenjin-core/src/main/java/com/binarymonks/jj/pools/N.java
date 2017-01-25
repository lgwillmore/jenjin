package com.binarymonks.jj.pools;

import com.binarymonks.jj.JJ;

/**
 * Created by lwillmore on 25/01/17.
 */
public class N {

    public static <T> T ew(Class<T> clazz) {
        return JJ.pools.nuw(clazz);
    }
}
