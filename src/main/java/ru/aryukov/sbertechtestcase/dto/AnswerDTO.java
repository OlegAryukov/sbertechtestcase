package ru.aryukov.sbertechtestcase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDTO {
    private String employWithMaxSalary;
    private Integer salarySum;
}
