package com.binarymonks.jj.specs.render;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.binarymonks.jj.render.GraphType;

public class GraphSpec implements Json.Serializable {

    public GraphType type = GraphType.DEFAULT;


    public GraphSpec(){}

    public void setToDefault() {
        type = GraphType.DEFAULT;
    }

    public void setToLightSource() {
        type = GraphType.LIGHT_SOURCE;
    }

    @Override
    public String toString() {
        return "GraphSpec{" +
                "type=" + type +
                '}';
    }

    @Override
    public void write(Json json) {
        json.writeValue("type", type, null, type.getClass());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        type=json.readValue(null, jsonData.get("type"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphSpec)) return false;

        GraphSpec graphSpec = (GraphSpec) o;

        return type.equals(graphSpec.type);

    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
