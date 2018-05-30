package com.prioritization.test.mapper;

import com.prioritization.test.domain.FaultMatrix;
import com.prioritization.test.epo.ResultRequestEpo;
import com.prioritization.test.geneticAlgorithm.Individual;
import org.springframework.stereotype.Service;

@Service
public class ResultRequestEpoMapper {

    public ResultRequestEpo toExternal(FaultMatrix faultMatrix, Individual individual) {
        return new ResultRequestEpo(faultMatrix, individual);
    }
}
