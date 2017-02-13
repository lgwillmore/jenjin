package com.binarymonks.jj;

import com.badlogic.gdx.utils.Json;
import org.junit.Assert;


public class JsonTest {


    public static void jsonRoundTrip(Object objectToSerialise) {
        Json json = new Json();

        String thingSpecJsonString = json.toJson(objectToSerialise);
        System.out.println(thingSpecJsonString);
        Object objectFromJson = json.fromJson(objectToSerialise.getClass(), thingSpecJsonString);

        Assert.assertEquals(objectToSerialise, objectFromJson);

    }


}
