package com.prioritization.test.geneticAlgorithm;

import java.util.Objects;

public class Individual implements Field<Field>{
     int nrTests;
    private int[] genes;
    // Cache
    private double fitness = 0.0;

    public Individual() {
        this.genes = new int[nrTests];
    }

    public Individual(int nrTests) {
        this.nrTests = nrTests;
        this.genes = new int[nrTests];
    }

    public void setNrTests(int nrTests) {
        this.nrTests = nrTests;
    }

    // Create a random individual
    public void generateIndividual() {
       int i=0;
        while (i<nrTests){
            int gene = (int)(Math.random() * ((nrTests-1) + 1));
            if (isGenerated(genes,gene,i))
                continue;
            else{
                genes[i] = gene;
                i++;
            }
        }
    }

    private Boolean isGenerated(int[] data, int x,int poz){
        int nrZ=0;
        int i=0;
        int j=0;
        while (i<poz){
            if (x==data[i]){return true;}
            i++;
        }
        return false;
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public  void setDefaultGeneLength(int length) {
        this.nrTests = length;
    }

    public int getGene(int index) {
        return genes[index];
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public  int getNrTests() {
        return nrTests;
    }

    public void setGene(int index, int value) {
        this.genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    @Override
    public double getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalculation.calculteAPFD(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Individual)) return false;
        Individual that = (Individual) o;
        return Double.compare(that.getFitness(), getFitness()) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFitness());
    }

    @Override
    public int compareTo(Field other) {
        if (this.getFitness()<other.getFitness())
            return 1;
        else if(other.getFitness()<this.getFitness())
            return -1;
        return 0;
    }
}
