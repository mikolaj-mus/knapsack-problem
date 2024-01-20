package org.example;

import java.util.Arrays;
import java.util.stream.IntStream;

class Chromosome {

    private int[] genes;
    private double fitness = 0;

    public Chromosome(int length) {
        genes = new int[length];
        genes = IntStream.generate(() -> (int) Math.round(Math.random()))
                .limit(length)
                .toArray();
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
