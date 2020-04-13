package ru.aryukov.sbertechtestcase.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aryukov.sbertechtestcase.dto.AnswerDTO;
import ru.aryukov.sbertechtestcase.dto.ParametersDTO;
import ru.aryukov.sbertechtestcase.model.Entry;
import ru.aryukov.sbertechtestcase.utils.EntryComparator;

@Component
public class SparkJobService implements SparkJobInterface {

    @Autowired
    private EntryComparator entryComparator;

    @Override
    public AnswerDTO getSparkJobs(ParametersDTO parametersDTO) {

        Logger.getLogger("org.apache").setLevel(Level.WARN);
        String adress = parametersDTO.getPathToJson().trim() + "/data/emp/*/*.json";
        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]");
        conf.set("spark.sql.warehouse.dir", "file:///" + parametersDTO.getSparkSqlWarehouseDir().trim()); //C:/MyJavaCode/SberTech/spark-warehouse
        System.setProperty("hadoop.home.dir", parametersDTO.getHadoopHomeDir().trim()); //C:/MyJavaCode/hadooponwindows-master/hadooponwindows-master
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        try {
            JavaRDD<Entry> entryJavaRDD = sparkContext.wholeTextFiles(adress)
                    .map(value -> {
                                JSONObject obj = new JSONObject(value._2);
                                return new Entry(value._1.split("/")[value._1.split("/").length - 2],
                                        obj.getString("name"),
                                        obj.getInt("salary")
                                );
                            }
                    );
            JavaRDD<String> map =
                    entryJavaRDD.map(entry -> new String(entry.getName() + "," + entry.getSalary() + "," + entry.getDepartment()));
            map.coalesce(1).saveAsTextFile(parametersDTO.getOutputPath().trim());
            Entry max = entryJavaRDD.max(entryComparator);
            Integer aggregate = entryJavaRDD.aggregate(0, (integer, entry) -> integer + entry.getSalary(), (integer,
                                                                                                            integer2) -> integer + integer2);
            return new AnswerDTO(max.getName(), aggregate);
        } finally {
            sparkContext.close();
        }

    }
}
