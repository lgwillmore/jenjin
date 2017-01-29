package com.binarymonks.jj.pools;

import com.binarymonks.jj.JJ;

public class Re {

    public static void cycle(Object object) {
        JJ.pools.recycle(object);
    }
}
