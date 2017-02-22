package com.binarymonks.jj.utils;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;

public class LinearAlgebra {


    public static float componentOfAinB(Vector2 a, Vector2 b) {
        Vector2 aCopy = N.ew(Vector2.class).set(a);
        Vector2 bCopy = N.ew(Vector2.class).set(b);
        float AdotB = aCopy.dot(bCopy);
        float scaleB = b.len();
        float comp = AdotB / scaleB;
        Re.cycle(aCopy, bCopy);
        return comp;
    }

}
