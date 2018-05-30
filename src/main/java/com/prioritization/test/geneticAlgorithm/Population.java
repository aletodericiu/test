package com.prioritization.test.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Population {

    List<Individual> fittestGuys=new ArrayList<>();
    Individual[] individuals;
    static int nrTests;

    public List<Individual> getFittestGuys() {
        return fittestGuys;
    }

    public static void setNrTests(int nrTests) {
        Population.nrTests = nrTests;
    }

    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise, int nrTests) {
        fittestGuys=new ArrayList<>();
        individuals = new Individual[populationSize];
        this.nrTests=nrTests;
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual(nrTests);
                newIndividual.setNrTests(nrTests);
                newIndividual.generateIndividual();
                System.out.println(newIndividual);
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() < getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    public List<Individual> getFittestThree() {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() < getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
                fittestGuys.add(fittest);
            }
        }
        return fittestGuys;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }

    @Override
    public String toString() {
        return "Population{" +
                "fittestGuys=" + fittestGuys +
                ", individuals=" + Arrays.toString(individuals) +
                '}';
    }
}
