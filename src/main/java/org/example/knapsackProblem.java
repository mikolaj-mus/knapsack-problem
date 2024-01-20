package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class knapsackProblem {

    private static final double BACKPACK_WEIGHT = 100.0;
    private static final double BACKPACK_VOLUME = 100.0;
    private static final int POPULATION_SIZE = 100;
    private static final double CROSSOVER_RATE = 0.5;
    private static final double MUTATION_RATE = 0.01;
    private static final int CHROMOSOME_LENGTH = 50;
    private static final int GENERATION = 100;
    private ArrayList<Chromosome> population;
    private final ArrayList<Item> items;
    private Chromosome bestChromosome;

    public knapsackProblem(ArrayList<Item> items) {
        this.items = items;
        this.bestChromosome = new Chromosome(CHROMOSOME_LENGTH);

        population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Chromosome(CHROMOSOME_LENGTH));
        }
    }

    public void run() {
        int i = 0;
        while (i < GENERATION) {

            for (Chromosome chromosome : population) {
                chromosome.setFitness(calculateFitness(chromosome));
            }
            ArrayList<Chromosome> winners = selectParent();

            ArrayList<Chromosome> newPopulation = new ArrayList<>();

            newPopulation.addAll(winners);

            for (int j = 0; j < winners.size(); j+=2) {
                Chromosome child1 = crossover(winners.get(j), winners.get(j+1));
                Chromosome child2 = crossover(winners.get(j+1), winners.get(j));

                mutate(child1);
                mutate(child2);

                newPopulation.add(child1);
                newPopulation.add(child2);
            }


            population = newPopulation;

            System.out.println("Najlepszy wynik " + (i + 1) + ". iteracji: " + bestChromosome.getFitness());
            i++;
        }
        System.out.println("Geny najlepszego chromosomu: " + Arrays.toString(bestChromosome.getGenes()));


    }

    private double calculateFitness(Chromosome chromosome) {
        double actualWeight = 0, actualVolume = 0, actualValue = 0;
        int[] genes = chromosome.getGenes();

        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 1) {
                Item item = items.get(i);
                actualWeight += item.getWeight();
                actualVolume += item.getVolume();
                actualValue += item.getValue();
            }
            if (actualVolume > BACKPACK_VOLUME || actualWeight > BACKPACK_WEIGHT) {
                return 0.0;
            }
        }

        if (actualValue > bestChromosome.getFitness()) {
            bestChromosome.setGenes(Arrays.copyOf(chromosome.getGenes(), CHROMOSOME_LENGTH));
            bestChromosome.setFitness(actualValue);
        }

        return actualValue;
    }

    private ArrayList<Chromosome> selectParent() {
        ArrayList<Chromosome> winners = new ArrayList<>(POPULATION_SIZE);

        for (int j = 0; j < POPULATION_SIZE/2; j++) {

            Chromosome parent1 = population.get((int) (Math.random() * POPULATION_SIZE));
            Chromosome parent2 = population.get((int) (Math.random() * POPULATION_SIZE));
            Chromosome winner = (parent1.getFitness() > parent2.getFitness()) ? parent1 : parent2;

            winners.add(winner);
        }
        return winners;
    }

    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {

        if (Math.random() > CROSSOVER_RATE) {
            return parent1;
        }

        int cutPoint = new Random().nextInt(CHROMOSOME_LENGTH);

        Chromosome child = new Chromosome(CHROMOSOME_LENGTH);
        for (int i = 0; i < cutPoint; i++) {
            child.getGenes()[i] = parent1.getGenes()[i];
        }
        for (int i = cutPoint; i < CHROMOSOME_LENGTH; i++) {
            child.getGenes()[i] = parent2.getGenes()[i];
        }

        return child;

    }

    private void mutate(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getGenes().length; i++) {
            if (Math.random() < MUTATION_RATE) {
                chromosome.getGenes()[i] = 1 - chromosome.getGenes()[i];
            }
        }
    }

    private static ArrayList<Item> generateFixedItems() {
        ArrayList<Item> items = new ArrayList<>();

        double[] weights = {2.0, 3.0, 5.0, 1.0, 4.0, 7.0, 2.0, 3.0, 6.0, 1.0,
                5.0, 8.0, 2.0, 4.0, 6.0, 1.0, 3.0, 7.0, 2.0, 4.0,
                2.0, 3.0, 5.0, 1.0, 4.0, 7.0, 2.0, 3.0, 6.0, 1.0,
                5.0, 8.0, 2.0, 4.0, 6.0, 1.0, 3.0, 7.0, 2.0, 4.0,
                2.0, 4.0, 8.0, 1.0, 7.0, 14.0, 2.0, 5.0, 11.0, 4.0};

        double[] volumes = {4.0, 6.0, 10.0, 2.0, 8.0, 14.0, 3.0, 5.0, 12.0, 1.0,
                7.0, 16.0, 4.0, 7.0, 11.0, 3.0, 5.0, 12.0, 4.0, 8.0,
                3.0, 5.0, 9.0, 4.0, 8.0, 11.0, 3.0, 5.0, 11.0, 4.0,
                6.0, 12.0, 2.0, 7.0, 11.0, 1.0, 3.0, 16.0, 4.0, 8.0,
                4.0, 8.0, 6.0, 3.0, 12.0, 20.0, 4.0, 8.0, 25.0, 7.0};

        double[] values = {8.0, 10.0, 15.0, 5.0, 12.0, 20.0, 7.0, 9.0, 18.0, 2.0,
                11.0, 25.0, 6.0, 13.0, 17.0, 4.0, 8.0, 16.0, 6.0, 12.0,
                7.0, 9.0, 15.0, 4.0, 11.0, 16.0, 6.0, 8.0, 17.0, 6.0,
                10.0, 20.0, 6.0, 13.0, 17.0, 4.0, 8.0, 16.0, 6.0, 12.0,
                6.0, 12.0, 18.0, 2.0, 16.0, 25.0, 6.0, 13.0, 15.0, 12.0};

        for (int i = 0; i < weights.length; i++) {
            items.add(new Item(weights[i], volumes[i], values[i]));
        }

        return items;
    }



    public static void main(String[] args) {

        ArrayList<Item> items = generateFixedItems();

        knapsackProblem knapsackProblem = new knapsackProblem(items);

        knapsackProblem.run();
    }


}

