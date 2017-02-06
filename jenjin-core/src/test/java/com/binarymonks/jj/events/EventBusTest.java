package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.async.Function;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;


public class EventBusTest {

    String message = "Arbitrary Message";
    ObjectMap<String, Object> emptyParams = new ObjectMap<>();
    private EventBus testObj;

    @Before
    public void setup() {
        this.testObj = new EventBus();
    }

    @Test
    public void sendNoParams() throws Exception {

        Function listenerMock = Mockito.mock(Function.class);
        EventHandler handler = Mockito.mock(EventHandler.class);

        testObj.register(message, listenerMock);
        testObj.register(message, handler);

        testObj.send(message);

        Mockito.verify(listenerMock).call();
        Mockito.verify(handler).handle(emptyParams);
    }

    @Test
    public void sendParams() {
        ObjectMap<String, Object> params = new ObjectMap<>();
        params.put("Arbitrary", "Value");
        Function listenerMock = Mockito.mock(Function.class);
        EventHandler handler = Mockito.mock(EventHandler.class);

        testObj.register(message, listenerMock);
        testObj.register(message, handler);

        testObj.send(message, params);

        Mockito.verify(listenerMock).call();
        Mockito.verify(handler).handle(params);
    }

}