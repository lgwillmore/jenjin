package com.binarymonks.jj.specs.render;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.SpecPropField;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.utils.Empty;

public abstract class RenderSpec implements Json.Serializable {
    public int id = Global.nextID();
    public int layer;
    public int priority;
    public GraphSpec renderGraph = new GraphSpec();
    public SpecPropField<Color> color = new SpecPropField<>(Color.WHITE);

    public abstract RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams);

    public Array<AssetReference> getAssets() {
        return Empty.Array();
    }

    @Override
    public String toString() {
        return "RenderSpec{" +
                "id=" + id +
                ", layer=" + layer +
                ", priority=" + priority +
                ", renderGraph=" + renderGraph +
                ", color=" + color +
                '}';
    }

    @Override
    public void write(Json json) {
        json.writeValue("layer", layer);
        json.writeValue("priority", priority);
        json.writeValue("renderGraph", renderGraph, null, renderGraph.getClass());
        json.writeValue("color", color, null, color.getClass());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        id = Global.nextID();
        layer = jsonData.getInt("layer");
        priority = jsonData.getInt("priority");
        renderGraph = json.readValue(null, jsonData.get("renderGraph"));
        color = json.readValue(null, jsonData.get("color"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RenderSpec)) return false;

        RenderSpec that = (RenderSpec) o;

        if (layer != that.layer) return false;
        if (priority != that.priority) return false;
        if (!renderGraph.equals(that.renderGraph)) return false;
        return color.equals(that.color);

    }

    @Override
    public int hashCode() {
        int result = layer;
        result = 31 * result + priority;
        result = 31 * result + renderGraph.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    public static class Null extends RenderSpec {


        @Override
        public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {
            return RenderNode.NULL;
        }

        @Override
        public void write(Json json) {

        }

        @Override
        public void read(Json json, JsonValue jsonData) {

        }
    }

}
