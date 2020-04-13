package ru.aryukov.sbertechtestcase.utils;

import org.springframework.stereotype.Component;
import ru.aryukov.sbertechtestcase.model.Entry;
import scala.Serializable;

import java.util.Comparator;

@Component
public class EntryComparator implements Comparator<Entry>, Serializable {

    @Override
    public int compare(Entry o1, Entry o2) {
        return o1.getSalary().compareTo(o2.getSalary());
    }
}
