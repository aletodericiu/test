package com.prioritization.test.service;

import com.prioritization.test.domain.FaultMatrix;
import com.prioritization.test.geneticAlgorithm.Algorithm;
import com.prioritization.test.geneticAlgorithm.FitnessCalculation;
import com.prioritization.test.geneticAlgorithm.Individual;
import com.prioritization.test.geneticAlgorithm.Population;
import com.prioritization.test.utils.FileHandling;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class FunctionalityService implements IFunctionalityService {

    private static final org.slf4j.Logger LOGGER   = LoggerFactory.getLogger(FunctionalityService.class);

    @Autowired
    private StorageService storageService;

    @Override
    public FaultMatrix getFaultMatrixFromFile() {
        FaultMatrix faultMatrix = null;
        File folder = storageService.getRootLocation().toFile();
        File[] listOfFiles = folder.listFiles();
        String filename= listOfFiles[0].getPath();
        try {
            faultMatrix = FileHandling.readMatrix(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return faultMatrix;
    }

    @Override
    public Individual getFittestIndivid() {
        FaultMatrix faultMatrix = null;
        File folder = storageService.getRootLocation().toFile();
        File[] listOfFiles = folder.listFiles();
        String filename= listOfFiles[0].getPath();
        try {
            faultMatrix = FileHandling.readMatrix(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(faultMatrix.toString());
        faultMatrix.printMatrix(faultMatrix.getData(),faultMatrix.getNumberOfFaults(),faultMatrix.getNumberOfTests());

        // Set a candidate solution
        FitnessCalculation.setFaultMatrix(faultMatrix);
        Algorithm.setFaultMatrix(faultMatrix);

        // Create an initial population
        Population myPop = new Population(150, true,faultMatrix.getNumberOfTests());
        // Evolve our population until we reach an optimum solution
        int i=0;
        while (i<100) {
            myPop = Algorithm.evolvePopulation(myPop);
            i++;
        }
        return myPop.getFittest();
    }

    @Override
    public List<Individual> getTopThreeFittest() {
        FaultMatrix faultMatrix = null;
        File folder = storageService.getRootLocation().toFile();
        File[] listOfFiles = folder.listFiles();
        String filename= listOfFiles[0].getPath();
        try {
            faultMatrix = FileHandling.readMatrix(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(faultMatrix.toString());
        faultMatrix.printMatrix(faultMatrix.getData(),faultMatrix.getNumberOfFaults(),faultMatrix.getNumberOfTests());

        // Set a candidate solution
        FitnessCalculation.setFaultMatrix(faultMatrix);
        Algorithm.setFaultMatrix(faultMatrix);

        // Create an initial population
        Population myPop = new Population(150, true,faultMatrix.getNumberOfTests());
        // Evolve our population until we reach an optimum solution
        int i=0;
        Set<Individual> set = new TreeSet<>();
        while (i<100) {
            myPop = Algorithm.evolvePopulation(myPop);
            set.add(myPop.getIndividual(i));
            i++;
        }
        List<Individual> actualresult= new ArrayList<>();
        actualresult.addAll(set);
        return actualresult.subList(0,3);
    }

    @Override
    public List<Integer> computeGraphicForIndividual(Individual individual) {
        FaultMatrix faultMatrix = null;
        File folder = storageService.getRootLocation().toFile();
        File[] listOfFiles = folder.listFiles();
        String filename= listOfFiles[0].getPath();
        try {
            faultMatrix = FileHandling.readMatrix(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] faultM=faultMatrix.getData();
        int[] genes=individual.getGenes();
        List<Integer> graphCoordinates= new ArrayList<>();
        LOGGER.info(faultMatrix.toString());
        int coordonate;
        for (int i=0;i<faultMatrix.getNumberOfTests(); i++) {
            coordonate=0;
            for (int j = 0; j < faultMatrix.getNumberOfFaults(); j++) {
                if ((faultM[genes[i]][j] == 1) && checkFaultCoveredByPreviousTests(faultMatrix,genes,i,j)==false){
                    coordonate++;
                }
            }
            graphCoordinates.add(coordonate);
        }

        return graphCoordinates;
    }

    private Boolean checkFaultCoveredByPreviousTests(FaultMatrix faultMatrix, int[] genes, int index, int j){
        int[][] faultM=faultMatrix.getData();
        for (int i=0;i<index; i++) {
            if (faultM[genes[i]][j] == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public Double calculateAPFDForAGivenVector(int[] data) {
        FaultMatrix faultMatrix = null;
        File folder = storageService.getRootLocation().toFile();
        File[] listOfFiles = folder.listFiles();
        String filename= listOfFiles[0].getPath();
        try {
            faultMatrix = FileHandling.readMatrix(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] vect= new int[faultMatrix.getNumberOfFaults()];
        Integer cursor=0;
        Boolean notFound=false;
        for (Integer i=0;i<faultMatrix.getNumberOfFaults();i++){
            cursor=0;
            notFound=false;
            while ((!notFound)&&(cursor<data.length)) {
                if (faultMatrix.getElementFromIndexes(data[cursor], i) == 1) {
                    vect[i]=cursor+1;
                    notFound=true;
                    cursor=0;
                }else{
                    cursor++;
                }
            }
        }
        int sum=0;
        for (Integer i=0;i<vect.length;i++){
            sum+=vect[i];
        }
        double result = (1-((double)sum/(faultMatrix.getNumberOfFaults()*faultMatrix.getNumberOfTests()))+((double)1/(2*faultMatrix.getNumberOfTests())));
        return result;
    }
}
