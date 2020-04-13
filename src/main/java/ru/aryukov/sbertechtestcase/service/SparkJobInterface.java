package ru.aryukov.sbertechtestcase.service;


import ru.aryukov.sbertechtestcase.dto.AnswerDTO;
import ru.aryukov.sbertechtestcase.dto.ParametersDTO;

public interface SparkJobInterface {
    AnswerDTO getSparkJobs(ParametersDTO parametersDTO);
}
