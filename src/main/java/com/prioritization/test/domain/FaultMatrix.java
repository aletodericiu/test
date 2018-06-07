package com.prioritization.test.domain;

import java.util.Arrays;
import java.util.Objects;

public class FaultMatrix {

    private Integer numberOfFaults;
    private Integer numberOfTests;
    private int[][] data;

    public FaultMatrix(Integer numberOfFaults, Integer numberOfTests,int[][] data) {
        this.numberOfFaults = numberOfFaults;
        this.numberOfTests = numberOfTests;
        this.data=data;
    }

    public Integer getNumberOfFaults() {
        return numberOfFaults;
    }

    public void setNumberOfFaults(Integer numberOfFaults) {
        this.numberOfFaults = numberOfFaults;
    }

    public Integer getNumberOfTests() {
        return numberOfTests;
    }

    public void setNumberOfTests(Integer numberOfTests) {
        this.numberOfTests = numberOfTests;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaultMatrix)) return false;
        FaultMatrix that = (FaultMatrix) o;
        return Objects.equals(getNumberOfFaults(), that.getNumberOfFaults()) &&
                Objects.equals(getNumberOfTests(), that.getNumberOfTests()) &&
                Arrays.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getNumberOfFaults(), getNumberOfTests());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    @Override
    public String toString() {
        return "FaultMatrix{" +
                "numberOfFaults=" + numberOfFaults +
                ", numberOfTests=" + numberOfTests +

                '}';
    }

    public void printMatrix(int[][] data, Integer numberOfFaults, Integer numberOfTests){
        for(int i=0;i<numberOfTests;i++){
            for(int j=0;j<numberOfFaults;j++){
                System.out.print(data[i][j]);
            }
            System.out.println("\n");
        }
    }

    public int getElementFromIndexes(Integer i,Integer j){
        return data[i][j];
    }
}