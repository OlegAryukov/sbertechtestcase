package ru.aryukov.sbertechtestcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.aryukov.sbertechtestcase.service.SparkJobService;

@RestController
public class InputDataController {

    @Autowired
    private SparkJobService sparkJobService;

    @GetMapping(path = "/getsparkjob")
    public void getSparkJobResult(@RequestBody String adressesList){
        sparkJobService.getSparkJobs(adressesList);
    }
}
