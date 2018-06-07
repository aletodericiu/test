package com.prioritization.test.controller;

//

import com.prioritization.test.domain.FaultMatrix;
import com.prioritization.test.geneticAlgorithm.Individual;
import com.prioritization.test.service.IFunctionalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "false")
public class ServiceController {

    @Autowired
    private IFunctionalityService             functionalityService;

    @RequestMapping (path = "/getfaultmatrix", method = RequestMethod.GET)
    public FaultMatrix getFaultMatrix() {

        return functionalityService.getFaultMatrixFromFile();
    }

    @RequestMapping (path = "/getbestindividual", method = RequestMethod.GET)
    public Individual getBestIndivid() {

        return functionalityService.getFittestIndivid();
    }

    @RequestMapping (path = "/getbestthreeindividuals", method = RequestMethod.GET)
    public List<Individual> getBestThreeIndivids() {

        return functionalityService.getTopThreeFittest();
    }

    @RequestMapping (path = "/getgraphcoordinates", method = RequestMethod.POST)
    public List<Integer> getGraphCoordinatesForIndividual(@RequestBody Individual individual) {

        return functionalityService.computeGraphicForIndividual(individual);
    }

    @RequestMapping (path = "/getAPFD", method = RequestMethod.POST)
    public Double getAPFDForAGivenVector(@RequestBody int[] data) {

        return functionalityService.calculateAPFDForAGivenVector(data);
    }
}
