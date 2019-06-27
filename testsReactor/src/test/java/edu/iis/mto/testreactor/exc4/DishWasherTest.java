package edu.iis.mto.testreactor.exc4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DishWasherTest {

    DirtFilter dirtFilter = mock(DirtFilter.class);
    WaterPump waterPump = mock(WaterPump.class);
    Engine engine = mock(Engine.class);
    Door door = mock(Door.class);

    DishWasher dishWasher;
    ProgramConfiguration programConfiguration;
    RunResult runResult;

    @Before
    public void init() {
        dishWasher = new DishWasher(waterPump, engine, dirtFilter, door);
        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(WashingProgram.INTENSIVE)
                                                   .withTabletsUsed(true)
                                                   .build();
    }

    @Test
    public void doorAreClosed() {
        when(door.closed()).thenReturn(true);
        runResult = dishWasher.start(programConfiguration);
        assertEquals(Status.DOOR_OPEN, runResult.getStatus());
    }

    @Test
    public void filterIsDirt() {
        when(dirtFilter.capacity()).thenReturn(40.0d);
        runResult = dishWasher.start(programConfiguration);
        assertEquals(Status.ERROR_FILTER, runResult.getStatus());
    }

    @Test
    public void programEndedWithSucces() {
        when(dirtFilter.capacity()).thenReturn(80.0d);
        runResult = dishWasher.start(programConfiguration);
        assertEquals(Status.SUCCESS, runResult.getStatus());

    }

    @Test
    public void engineException() throws Exception {
        when(dirtFilter.capacity()).thenReturn(80.0d);
        doThrow(EngineException.class).when(engine)
                                      .runProgram(Mockito.any());
        runResult = dishWasher.start(programConfiguration);
        assertEquals(Status.ERROR_PROGRAM, runResult.getStatus());
    }

    @Test
    public void pumpException() throws Exception {
        when(dirtFilter.capacity()).thenReturn(80.0d);
        doThrow(PumpException.class).when(waterPump)
                                    .drain();
        runResult = dishWasher.start(programConfiguration);
        assertEquals(Status.ERROR_PUMP, runResult.getStatus());
    }

    @Test
    public void filterIsChecking() {
        when(dirtFilter.capacity()).thenReturn(80.0d);
        runResult = dishWasher.start(programConfiguration);
        verify(dirtFilter, times(1)).capacity();
    }

    @Test
    public void filterIsNotChecking() {
        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(WashingProgram.INTENSIVE)
                                                   .withTabletsUsed(false)
                                                   .build();
        when(dirtFilter.capacity()).thenReturn(80.0d);
        runResult = dishWasher.start(programConfiguration);
        verify(dirtFilter, times(0)).capacity();
    }
}
