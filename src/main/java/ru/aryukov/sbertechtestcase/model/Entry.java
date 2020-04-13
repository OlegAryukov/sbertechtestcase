package ru.aryukov.sbertechtestcase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import scala.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Entry implements Serializable, java.io.Serializable {
    private String department;
    private String name;
    private Integer salary;
}
