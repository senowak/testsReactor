package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import edu.iis.mto.testreactor.exc1.CoffeOrder.Builder;

public class CoffeeMachineTest {

    Grinder grinder = new CoffeeGrinder();

    MilkProvider milkProvider = Mockito.mock(MilkProvider.class);

    CoffeeReceipes receipes = Mockito.mock(CoffeeReceipes.class);

    private CoffeeMachine CF = new CoffeeMachine(grinder, milkProvider, receipes);
    private Coffee coffee;

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void CoffeeMakeWaterTest() {
        coffee = CF.make(CoffeOrder.builder()
                                   .withSize(CoffeeSize.SMALL)
                                   .withType(CoffeType.ESPRESSO)
                                   .build());

        Assert.assertThat(coffee.getWaterAmount(), is(1));
    }

}

    class CoffeeGrinder  implements Grinder{

        @Override
        public boolean grind(CoffeeSize size) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    class CoffeeRecipess implements CoffeeReceipes{

        @Override
        public CoffeeReceipe getReceipe(CoffeType type) {
            // TODO Auto-generated method stub
            return null;
        }

    }



