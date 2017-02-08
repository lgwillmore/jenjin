package com.binarymonks.jj.behaviour;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lwillmore on 03/02/17.
 */
public class BehaviourRootTest {
    @Test
    public void add_type_is_supertype() throws Exception {
        BehaviourRoot testObj = new BehaviourRoot();
        testObj.add(new MySuperBehaviour());
    }

    @Test(expected = BehaviourRoot.InvalidTypeInheritanceException.class)
    public void add_type_is_not_supertype() {
        BehaviourRoot testObj = new BehaviourRoot();
        testObj.add(new MySubType());
    }

    public static class MySuperBehaviour extends Behaviour {

        @Override
        public void getReady() {

        }

        @Override
        public void update() {

        }

        @Override
        public void tearDown() {

        }

        @Override
        public Behaviour clone() {
            return null;
        }

        @Override
        public Class<?> type() {
            return Behaviour.class;
        }
    }

    public static class MySubType extends Behaviour {

        @Override
        public void getReady() {

        }

        @Override
        public void update() {

        }

        @Override
        public void tearDown() {

        }

        @Override
        public Behaviour clone() {
            return null;
        }

        @Override
        public Class<?> type() {
            return MySuperBehaviour.class;
        }
    }

}