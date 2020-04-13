package ru.aryukov.sbertechtestcase.dto;


import lombok.Data;

import java.util.List;

@Data
public class ParametersDTO {
    private String pathToJson;
    private String sparkSqlWarehouseDir;
    private String outputPath;
    private String hadoopHomeDir;
}
