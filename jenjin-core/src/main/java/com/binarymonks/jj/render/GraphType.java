package com.binarymonks.jj.render;

public interface GraphType {

    public static GraphType DEFAULT = new Default();
    public static GraphType LIGHT_SOURCE = new LightSource();

    public String name();

    class Default implements GraphType {

        @Override
        public String name() {
            return RenderWorld.DEFAULT_RENDER_GRAPH;
        }
    }

    class LightSource implements GraphType {

        @Override
        public String name() {
            return RenderWorld.LIGHTSOURCE_RENDER_GRAPH;
        }
    }

    public static class Custom implements GraphType {
        String name;

        public Custom(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }


}
