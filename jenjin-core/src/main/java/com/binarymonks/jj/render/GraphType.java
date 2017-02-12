package com.binarymonks.jj.render;

public interface GraphType {

    public static GraphType DEFAULT = new Default();
    public static GraphType LIGHT_SOURCE = new LightSource();

    class Default implements GraphType {

    }

    class LightSource implements GraphType {

    }

    public static class Custom implements GraphType {
        String name;

        public Custom(String name) {
            this.name = name;
        }
    }


}
