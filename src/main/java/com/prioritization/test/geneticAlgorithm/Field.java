package com.prioritization.test.geneticAlgorithm;

public interface Field<T> extends Comparable<T> {
    public double getFitness();
}