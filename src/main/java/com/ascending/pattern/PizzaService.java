package com.ascending.pattern;

public class PizzaService {
    private Pizza.PizzaBuilder builder;

    public PizzaService(Pizza.PizzaBuilder builder) {
        this.builder = builder;
    }

    public Pizza orderHouseSpecial() {
        return builder.name("Special")
                .size(18)
                .extraTopping("Mushrooms")
                .stuffedCrust(true)
                .extraTopping("Chilli")
                .collect(true)
                .discount(20)
                .build();
    }

    public Pizza createPizzaWithName(String name) {
        Pizza pizza = new Pizza
                .PizzaBuilder()
                .name(name)
                .size(12)
                .extraTopping("Mushroom")
                .stuffedCrust(false)
                .collect(true)
                .discount(20)
                .build();
        return pizza;
    }

}

