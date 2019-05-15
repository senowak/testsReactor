package edu.iis.mto.testreactor.exc2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WashingMachineTest {

    DirtDetector dirtDetector = mock(DirtDetector.class);
    Engine engine = mock(Engine.class);
    WaterPump waterPump = mock(WaterPump.class);

    LaundryBatch laundryBatch;
    ProgramConfiguration programConfiguration;

    WashingMachine washingMachine;

    @BeforeEach
    public void init(){
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        laundryBatch = laundryBatch.builder().withType(Material.COTTON).withWeightKg(1.0).build();
        programConfiguration = programConfiguration.builder().withProgram(Program.AUTODETECT).withSpin(true).build();
    }


    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

    @Test
    public void checkForOverweightBatch(){
        LaundryBatch localLaundryBatch = laundryBatch.builder().withType(Material.COTTON).withWeightKg(9.0).build();
        ProgramConfiguration localProgramConfiguration = programConfiguration.builder().withProgram(Program.LONG).withSpin(true).build();

        LaundryStatus laundryStatus = LaundryStatus.builder()
                .withResult(Result.FAILURE)
                .withErrorCode(ErrorCode.TOO_HEAVY)
                .build();

        assertEquals(laundryStatus.toString(), washingMachine.start(localLaundryBatch, localProgramConfiguration).toString());
    }

    @Test
    public void autodetectExpectLongForDirtyGreaterThanAverage(){
        LaundryBatch localLaundryBatch = laundryBatch.builder().withType(Material.COTTON).withWeightKg(2.0).build();
        ProgramConfiguration localProgramConfiguration = programConfiguration.builder().withProgram(Program.AUTODETECT).withSpin(true).build();

        when(dirtDetector.detectDirtDegree(localLaundryBatch)).thenReturn(new Percentage(50.0));

        LaundryStatus laundryStatus = washingMachine.start(localLaundryBatch, localProgramConfiguration);
        assertEquals(Program.LONG, laundryStatus.getRunnedProgram());
    }

    @Test
    public void autodetectExpectMediumForDirtyGreaterThanAverage(){
        LaundryBatch localLaundryBatch = laundryBatch.builder().withType(Material.COTTON).withWeightKg(2.0).build();
        ProgramConfiguration localProgramConfiguration = programConfiguration.builder().withProgram(Program.AUTODETECT).withSpin(true).build();

        when(dirtDetector.detectDirtDegree(localLaundryBatch)).thenReturn(new Percentage(40.0));

        LaundryStatus laundryStatus = washingMachine.start(localLaundryBatch, localProgramConfiguration);
        assertEquals(Program.MEDIUM, laundryStatus.getRunnedProgram());
    }

}
