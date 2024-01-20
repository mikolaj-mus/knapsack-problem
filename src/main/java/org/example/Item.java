package org.example;

class Item {

    private double weight;
    private double volume;
    private double value;

    public Item(double weight, double volume, double value) {
        this.weight = weight;
        this.volume = volume;
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
