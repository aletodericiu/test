package com.prioritization.test.service;

import com.prioritization.test.epo.ResultRequestEpo;
import com.prioritization.test.geneticAlgorithm.Individual;

import java.util.List;

public interface IFunctionalityService {

    public ResultRequestEpo getFittestIndivid(String filepath);

    public List<Individual> getTopThreeFittest(String filepath);
}
