package ru.aryukov.sbertechtestcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.aryukov.sbertechtestcase.dto.AnswerDTO;
import ru.aryukov.sbertechtestcase.dto.ParametersDTO;
import ru.aryukov.sbertechtestcase.service.SparkJobService;

@RestController
public class InputDataController {

    @Autowired
    private SparkJobService sparkJobService;

    @PostMapping(path = "/getsparkjob", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerDTO> getSparkJobResult(@RequestBody ParametersDTO parametersDTO) {
        return new ResponseEntity<>(sparkJobService.getSparkJobs(parametersDTO), HttpStatus.OK);
    }
}
