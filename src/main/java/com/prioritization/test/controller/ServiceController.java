package com.prioritization.test.controller;

//

import com.prioritization.test.epo.ResultRequestEpo;
import com.prioritization.test.geneticAlgorithm.Individual;
import com.prioritization.test.service.IFunctionalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private IFunctionalityService             functionalityService;

    @RequestMapping(path = "/ge", method = RequestMethod.GET)
    public String getAllUsers() {

        return "salut";
    }

    @RequestMapping (value = "/getbestindivid", method = RequestMethod.POST)
    public ResultRequestEpo getBestIndivid(@RequestBody String filepath) {

        return functionalityService.getFittestIndivid(filepath);
    }

    @RequestMapping (value = "/getbestthreeindivids", method = RequestMethod.POST)
    public List<Individual> getBestThreeIndivids(@RequestBody String filepath) {

        return functionalityService.getTopThreeFittest(filepath);
    }
}
