package com.group12.Backend;

public enum Fruits {

    APPLE("Apple", "RED"),
    ORANGE("Orange", "ORANGE");

    private final String name;
    private final String color;

    Fruits(String name, String color) {

        this.name = name;
        this.color = color;

    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

}
