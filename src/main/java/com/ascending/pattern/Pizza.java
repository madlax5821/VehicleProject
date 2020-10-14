package com.ascending.pattern;

public class Pizza {
    private String name;
    private int size;
    private String extraTopping;
    private boolean stuffedCrust;
    private boolean collect;
    private int discount;

    public static class PizzaBuilder {
        private String name;
        private int size;
        private String extraTopping;
        private boolean stuffedCrust;
        private boolean collect;
        private int discount;

        public PizzaBuilder(String name) {
            this.name = name;
        }
        public PizzaBuilder() {       }

        public PizzaBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PizzaBuilder size(int size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder extraTopping(String extraTopping) {
            this.extraTopping = extraTopping;
            return this;
        }

        public PizzaBuilder stuffedCrust(boolean stuffedCrust) {
            this.stuffedCrust = stuffedCrust;
            return this;
        }

        public PizzaBuilder collect(boolean collect) {
            this.collect = collect;
            return this;
        }

        public PizzaBuilder discount(int discount) {
            this.discount = discount;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    private Pizza(PizzaBuilder pizzaBuilder) {
        this.name = pizzaBuilder.name;
        this.size = pizzaBuilder.size;
        this.collect = pizzaBuilder.collect;
        this.extraTopping = pizzaBuilder.extraTopping;
        this.discount = pizzaBuilder.discount;
        this.stuffedCrust = pizzaBuilder.stuffedCrust;
    }

    public Pizza(String name) {
        this.name = name;
    }

    public Pizza() {    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getExtraTopping() {
        return extraTopping;
    }

    public void setExtraTopping(String extraTopping) {
        this.extraTopping = extraTopping;
    }

    public boolean isStuffedCrust() {
        return stuffedCrust;
    }

    public void setStuffedCrust(boolean stuffedCrust) {
        this.stuffedCrust = stuffedCrust;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

