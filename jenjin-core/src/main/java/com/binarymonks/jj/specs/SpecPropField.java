package com.binarymonks.jj.specs;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SpecPropField<VALUE, OWNER> implements FieldPropertyDelegate<VALUE, OWNER>, Json.Serializable {

    VALUE value;
    OWNER owner;
    String propertyDelegate;

    public SpecPropField() {
    }


    public SpecPropField(OWNER owner, VALUE value) {
        this.value = value;
        this.owner = owner;
    }

    public SpecPropField(OWNER owner) {
        this.owner = owner;
    }

    @Override
    public OWNER set(VALUE value) {
        this.value = value;
        return owner;
    }

    @Override
    public OWNER delegateToProperty(String propertykey) {
        this.propertyDelegate = propertykey;
        return owner;
    }

    @Override
    public void write(Json json) {
        json.writeValue("value", value, null, value.getClass());
        json.writeValue("propertyDelegate", propertyDelegate);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        value = json.readValue(null, jsonData.get("value"));
        propertyDelegate = jsonData.getString("propertyDelegate");
    }

    @Override
    public String toString() {
        return "SpecPropField{" +
                "value=" + value +
                ", propertyDelegate='" + propertyDelegate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecPropField)) return false;

        SpecPropField<?, ?> that = (SpecPropField<?, ?>) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return propertyDelegate != null ? propertyDelegate.equals(that.propertyDelegate) : that.propertyDelegate == null;

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (propertyDelegate != null ? propertyDelegate.hashCode() : 0);
        return result;
    }
}
