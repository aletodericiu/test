package com.prioritization.test.epo;

import com.prioritization.test.domain.FaultMatrix;
import com.prioritization.test.geneticAlgorithm.Individual;

import java.io.Serializable;
import java.util.Objects;

public class ResultRequestEpo implements Serializable {

    private FaultMatrix faultMatrix;
    private Individual fittestIndivid;

    public ResultRequestEpo(FaultMatrix faultMatrix, Individual fittestIndivid) {
        this.faultMatrix = faultMatrix;
        this.fittestIndivid = fittestIndivid;
    }

    public FaultMatrix getFaultMatrix() {
        return faultMatrix;
    }

    public Individual getFittestIndivid() {
        return fittestIndivid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultRequestEpo)) return false;
        ResultRequestEpo that = (ResultRequestEpo) o;
        return Objects.equals(getFaultMatrix(), that.getFaultMatrix()) &&
                Objects.equals(getFittestIndivid(), that.getFittestIndivid());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFaultMatrix(), getFittestIndivid());
    }

    @Override
    public String toString() {
        return "ResultRequestEpo{" +
                "faultMatrix=" + faultMatrix +
                ", fittestIndivid=" + fittestIndivid +
                '}';
    }
}
