package edu.iis.mto.testreactor.exc1;

import java.util.Objects;

public class CoffeeMachine {

    private final Grinder grinder;
    private final MilkProvider milkProvider;
    private final CoffeeReceipes receipes;

    public CoffeeMachine(Grinder grinder, MilkProvider milkProvider, CoffeeReceipes receipes) {
        this.grinder = Objects.requireNonNull(grinder, "ginder == null");
        this.milkProvider = Objects.requireNonNull(milkProvider, "milkProvider == null");
        this.receipes = Objects.requireNonNull(receipes, "receipes == null");
    }

    public Coffee make(CoffeOrder order) {
        validate(order);
        grindCoffee(order);
        Coffee coffee = create(order);
        addMilk(order, coffee);
        return coffee;
    }

    private void validate(CoffeOrder order) {
        CoffeeReceipe receipe = receipes.getReceipe(order.getType());
        if (Objects.isNull(receipe)) {
            throw new UnknownCofeeTypeException("no receipe for order " + order);
        }
    }

    private void addMilk(CoffeOrder order, Coffee coffee) {
        if (isMilkCoffee(order.getType())) {
            int milkAmount = receipes.getReceipe(order.getType())
                                     .getMilkAmount();
            milkProvider.pour(milkAmount);
            coffee.setMilkAmout(milkAmount);
        }
    }

    private boolean isMilkCoffee(CoffeType type) {
        return receipes.getReceipe(type)
                       .withMilk();
    }

    private void grindCoffee(CoffeOrder order) {
        if (!grinder.grind(order.getSize())) {
            throw new NoCoffeeBeansException();
        }
    }

    private Coffee create(CoffeOrder order) {
        CoffeeReceipe receipe = receipes.getReceipe(order.getType());
        Integer waterAmount = receipe.getWaterAmount(order.getSize());
        Coffee coffee = new Coffee();
        coffee.setWaterAmount(waterAmount);
        return coffee;
    }
}
