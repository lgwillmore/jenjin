package com.binarymonks.jj.async;

import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;

public class FunctionLink implements Function {

    Function doAfter;
    Function doBefore;

    FunctionLink() {
    }

    @Override
    public void call() {
        doBefore.call();
        doAfter.call();
        Re.cycle(this);
    }

    public FunctionLink thenDo(Function function) {
        this.doAfter = function;
        return this;
    }

    public static FunctionLink Do(Function doBefore) {
        FunctionLink fc =  N.ew(FunctionLink.class);
        fc.doBefore = doBefore;
        return fc;
    }


    public static class FunctionLinkPoolManager implements PoolManager<FunctionLink> {

        @Override
        public void reset(FunctionLink functionLink) {
            functionLink.doBefore = null;
            functionLink.doAfter = null;
        }

        @Override
        public FunctionLink create_new() {
            return new FunctionLink();
        }

        @Override
        public void dispose(FunctionLink functionLink) {

        }
    }
}
