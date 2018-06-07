package com.prioritization.test.service;

import com.prioritization.test.domain.FaultMatrix;
import com.prioritization.test.geneticAlgorithm.Individual;

import java.util.List;

public interface IFunctionalityService {

    public FaultMatrix getFaultMatrixFromFile();

    public Individual getFittestIndivid();

    public List<Individual> getTopThreeFittest();

    public List<Integer> computeGraphicForIndividual(Individual individual);

    public Double calculateAPFDForAGivenVector(int[] data);
}
