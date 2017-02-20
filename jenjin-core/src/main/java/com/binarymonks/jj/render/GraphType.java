package com.binarymonks.jj.render;

public interface GraphType {

    GraphType DEFAULT = new Default();
    GraphType LIGHT_SOURCE = new LightSource();

    String name();

    class Default implements GraphType {

        @Override
        public String name() {
            return RenderWorld.DEFAULT_RENDER_GRAPH;
        }

        @Override
        public String toString() {
            return "Default{}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Default)) return false;
            return true;
        }

    }

    class LightSource implements GraphType {

        @Override
        public String name() {
            return RenderWorld.LIGHTSOURCE_RENDER_GRAPH;
        }

        @Override
        public String toString() {
            return "LightSource{}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LightSource)) return false;
            return true;
        }
    }

    class Custom implements GraphType {
        String name;

        public Custom(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String toString() {
            return "Custom{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


}
