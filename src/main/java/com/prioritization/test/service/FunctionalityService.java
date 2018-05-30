package com.prioritization.test.service;

import com.prioritization.test.domain.FaultMatrix;
import com.prioritization.test.epo.ResultRequestEpo;
import com.prioritization.test.geneticAlgorithm.Algorithm;
import com.prioritization.test.geneticAlgorithm.FitnessCalculation;
import com.prioritization.test.geneticAlgorithm.Individual;
import com.prioritization.test.geneticAlgorithm.Population;
import com.prioritization.test.mapper.ResultRequestEpoMapper;
import com.prioritization.test.utils.FileHandling;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FunctionalityService implements IFunctionalityService {

    private static final org.slf4j.Logger LOGGER   = LoggerFactory.getLogger(FunctionalityService.class);

    @Autowired
    private ResultRequestEpoMapper resultRequestEpoMapper;

    @Override
    public ResultRequestEpo getFittestIndivid(String filepath) {
        FaultMatrix faultMatrix = null;
        try {
            faultMatrix = FileHandling.readMatrix("src/main/resources/faultMatrix.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LOGGER.info(faultMatrix.toString());
        faultMatrix.printMatrix(faultMatrix.getData(),faultMatrix.getNumberOfFaults(),faultMatrix.getNumberOfTests());

        // Set a candidate solution
        FitnessCalculation.setFaultMatrix(faultMatrix);
        Algorithm.setFaultMatrix(faultMatrix);

        // Create an initial population
        //Population myPop = new Population(UsefulMethods.factorial(faultMatrix.getNumberOfTests()), true,faultMatrix.getNumberOfTests());
        Population myPop = new Population(150, true,faultMatrix.getNumberOfTests());
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        int i=0;
//        while (myPop.getFittest().getFitness() >= FitnessCalculation.getMaxFitness()) {
        while (i<100) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
            i++;
        }
        for (i=0;i<myPop.size();i++){
            LOGGER.info(myPop.getIndividual(i).getFitness()+"");
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        System.out.println(myPop.getFittest());
        System.out.println(FitnessCalculation.calculteAPFD(myPop.getFittest()));
        return resultRequestEpoMapper.toExternal(faultMatrix,myPop.getFittest());
    }

    @Override
    public List<Individual> getTopThreeFittest(String filepath) {
        FaultMatrix faultMatrix = null;
        try {
            faultMatrix = FileHandling.readMatrix("src/main/resources/faultMatrix.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LOGGER.info(faultMatrix.toString());
        faultMatrix.printMatrix(faultMatrix.getData(),faultMatrix.getNumberOfFaults(),faultMatrix.getNumberOfTests());

        // Set a candidate solution
        FitnessCalculation.setFaultMatrix(faultMatrix);
        Algorithm.setFaultMatrix(faultMatrix);

        // Create an initial population
        //Population myPop = new Population(UsefulMethods.factorial(faultMatrix.getNumberOfTests()), true,faultMatrix.getNumberOfTests());
        Population myPop = new Population(150, true,faultMatrix.getNumberOfTests());
        // Evolve our population until we reach an optimum solution
        int generationCount=0;
        int i=0;
//        while (myPop.getFittest().getFitness() >= FitnessCalculation.getMaxFitness()) {
        while (i<100) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
            i++;
        }
        for (i=0;i<myPop.size();i++){
            LOGGER.info(myPop.getIndividual(i).getFitness()+"");
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        System.out.println(myPop.getFittest());
        System.out.println(FitnessCalculation.calculteAPFD(myPop.getFittest()));
        List<Individual> result= myPop.getFittestThree();
        Collections.sort(result, new Comparator<Individual>() {
            @Override
            public int compare(Individual i2, Individual i1)
            {

                if (i1.getFitness()<i2.getFitness())
                    return -1;
                else if(i2.getFitness()<i1.getFitness())
                    return 1;
                return 0;
            }
        });
        List<Individual> actualresult=result.subList(0,2);
        return result;
    }
}
