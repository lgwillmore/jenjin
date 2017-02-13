package com.binarymonks.jj.render.specs;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class SpatialRenderSpec<CONCRETE extends SpatialRenderSpec> extends RenderSpec<CONCRETE> implements Json.Serializable {
    public Spatial spatial = new Spatial.Fixed();

    public CONCRETE setSpatial(Spatial spatial) {
        this.spatial = spatial;
        return self;
    }

    @Override
    public String toString() {
        return "SpatialRenderSpec{" +
                "spatial=" + spatial +
                "} " + super.toString();
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("spatial", spatial, null, spatial.getClass());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        spatial = json.readValue(null, jsonData.get("spatial"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpatialRenderSpec)) return false;
        if (!super.equals(o)) return false;

        SpatialRenderSpec<?> that = (SpatialRenderSpec<?>) o;

        return spatial.equals(that.spatial);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + spatial.hashCode();
        return result;
    }
}
