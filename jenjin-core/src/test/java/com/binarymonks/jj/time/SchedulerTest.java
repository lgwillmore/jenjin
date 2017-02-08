package com.binarymonks.jj.time;

import com.binarymonks.jj.async.Function;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class SchedulerTest {

    Scheduler schedular;

    Supplier<Double> timeMock;
    Function scheduledFunctionMock;

    @Before
    public void setup() {
        timeMock = Mockito.mock(Supplier.class);
        schedular = new Scheduler(timeMock);
        scheduledFunctionMock = Mockito.mock(Function.class);
    }

    @Test
    public void update_scheduledFunctionNotCalledEarlyAndCalledOnce() {
        double timeScheduled = 0.0;
        float timeToWait = 5.0f;

        setTimeTo(timeScheduled);

        schedular.scheduleInSeconds(scheduledFunctionMock, timeToWait);

        setTimeTo(timeScheduled + 4.9f);

        schedular.update();

        Mockito.verifyZeroInteractions(scheduledFunctionMock);

        setTimeTo(timeScheduled + timeToWait);
        schedular.update();

        Mockito.verify(scheduledFunctionMock).call();

        schedular.update();
        Mockito.verifyNoMoreInteractions(scheduledFunctionMock);


    }

    @Test
    public void update_scheduledFunctionNotCalledIfCancelled() {
        double timeScheduled = 0.0;
        float timeToWait = 5.0f;

        setTimeTo(timeScheduled);

        int id = schedular.scheduleInSeconds(scheduledFunctionMock, timeToWait);

        setTimeTo(timeScheduled + 4.9f);

        schedular.update();

        Mockito.verifyZeroInteractions(scheduledFunctionMock);

        schedular.cancelScheduled(id);

        setTimeTo(timeScheduled + timeToWait);
        schedular.update();

        Mockito.verifyZeroInteractions(scheduledFunctionMock);


    }


    private void setTimeTo(double timeScheduled) {
        Mockito.when(timeMock.get()).thenReturn(timeScheduled);
    }

}