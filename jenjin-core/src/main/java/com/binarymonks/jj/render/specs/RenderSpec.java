package com.binarymonks.jj.render.specs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.SpecPropField;
import com.binarymonks.jj.utils.Empty;

public abstract class RenderSpec<CONCRETE extends RenderSpec> implements Json.Serializable {
    public int id = Global.nextID();
    public int layer;
    public int thingPriority;
    public GraphSpec<CONCRETE> renderGraph = new GraphSpec<CONCRETE>((CONCRETE) this);
    public SpecPropField<Color, CONCRETE> color = new SpecPropField<>((CONCRETE) this, Color.WHITE);
    CONCRETE self;

    public RenderSpec() {
        self = (CONCRETE) this;
    }

    public CONCRETE setLayer(int layer) {
        this.layer = layer;
        return self;
    }

    public CONCRETE setPriority(int priority) {
        this.thingPriority = priority;
        return self;
    }

    public abstract RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec);

    public Array<AssetReference> getAssets() {
        return Empty.Array();
    }

    @Override
    public String toString() {
        return "RenderSpec{" +
                "id=" + id +
                ", layer=" + layer +
                ", thingPriority=" + thingPriority +
                ", renderGraph=" + renderGraph +
                ", color=" + color +
                '}';
    }

    @Override
    public void write(Json json) {
        json.writeValue("layer", layer);
        json.writeValue("thingPriority", thingPriority);
        json.writeValue("renderGraph", renderGraph, null, renderGraph.getClass());
        json.writeValue("color", color, null, color.getClass());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        id = Global.nextID();
        layer = jsonData.getInt("layer");
        thingPriority = jsonData.getInt("thingPriority");
        renderGraph = json.readValue(null, jsonData.get("renderGraph"));
        color = json.readValue(null, jsonData.get("color"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RenderSpec)) return false;

        RenderSpec<?> that = (RenderSpec<?>) o;

        if (layer != that.layer) return false;
        if (thingPriority != that.thingPriority) return false;
        if (!renderGraph.equals(that.renderGraph)) return false;
        return color.equals(that.color);

    }

    @Override
    public int hashCode() {
        int result = layer;
        result = 31 * result + thingPriority;
        result = 31 * result + renderGraph.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    public static class Null extends RenderSpec<Null> {


        @Override
        public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec) {
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
