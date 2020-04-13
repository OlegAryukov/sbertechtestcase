package ru.aryukov.sbertechtestcase.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aryukov.sbertechtestcase.model.Entry;
import ru.aryukov.sbertechtestcase.utils.EntryComparator;

@Component
public class SparkJobService implements SparkJobInterface {

    @Autowired
    private EntryComparator entryComparator;
    @Override
    public void getSparkJobs(String adresses) {
        //        List<String> inputDate = new ArrayList<>();
//        inputDate.add("WARN: TU 4 Sept 0405");
//        inputDate.add("ERROR: TU 4 Sept 0408");
//        inputDate.add("FATAL: WED 4 Sept 1632");
//        inputDate.add("ERROR: FR 4 Sept 1854");
//        inputDate.add("WARN: ST 4 Sept 1942");

        Logger.getLogger("org.apache").setLevel(Level.WARN);
        String adress = adresses.trim() + "/data/emp/*/*.json";
        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]");
        conf.set("spark.sql.warehouse.dir", "file:///D:/Programming/coursehunter/Spark/SberTech/sbertechtestcase/spark-warehouse");
        System.setProperty("hadoop.home.dir", "D:\\Programming\\coursehunter\\Spark");
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
            entryJavaRDD.foreach(s -> System.out.println(s));
            JavaRDD<String> map = entryJavaRDD.map(entry -> new String(entry.getDepartment() + "," + entry.getName() + "," + entry.getSalary()));
            SQLContext sqlContext = new SQLContext(sparkContext);
            Dataset<Row> dataFrame = sqlContext.createDataFrame(map, Entry.class);
            dataFrame.coalesce(1)
                    .write()
                    .option("header", "false")
                    .option("delimiter", ",")
                    .format("com.databricks.spark.csv")
                    .mode("overwrite")
                    .csv("test1.csv");
//                    map.saveAsTextFile("D:/Programming/coursehunter/Spark/SberTech/sbertechtestcase/src/main/resources/test1");

            Entry max = entryJavaRDD.max(entryComparator);
            System.out.println(max.getName());
            Integer aggregate = entryJavaRDD.aggregate(0, (integer, entry) -> integer + entry.getSalary(), (integer, integer2) -> integer + integer2);
            System.out.println(aggregate);

            //entryJavaRDD.saveAsTextFile("D:/Programming/coursehunter/Spark/SberTech/sbertechtestcase/src/main/resources/test1.csv");
        } finally {
            sparkContext.close();
        }

        //Dataset<Row> df = sparkContext.r
        //JavaRDD<String> initRead =
        //textFile("D:/Programming/coursehunter/Spark/SberTech/sbertechtestcase/inputDate.txt");
        //initRead.mapToPair()
        // JavaRDD<String> originalLogMessages =
//        sparkContext.parallelize(inputDate)
//                .mapToPair(rawValue -> new Tuple2<>(rawValue.split(":")[0], 1L))
//                .reduceByKey((value1, value2) -> value1 + value2)
//                .foreach(tuple -> System.out.println(tuple._1 + " has " + tuple._2 + " instance"));

//        JavaPairRDD<String, Long> logLevelDatePairRdd = originalLogMessages.mapToPair(rawValue -> {
//            String[] columns = rawValue.split(":");
//            String level = columns[0];
//            return new Tuple2<>(level, 1L);
//        });
//        //JavaRDD<Tuple2<Integer,Double>> valueSqrt = originalString.map(value -> new Tuple2<>(value, Math.sqrt(value)));
//
//        System.out.println("Try to start logg");
//        JavaPairRDD<String, Long> stringLongJavaPairRDD = logLevelDatePairRdd.reduceByKey((value1, value2) -> value1 + value2);
//        stringLongJavaPairRDD.foreach(tuple -> System.out.println(tuple._1 + " has " + tuple._2 + " instance"));
//        System.out.println("Try to end logg");

//        JavaRDD<String> stringJavaRDD = sparkContext.parallelize(inputDate).
//                flatMap(value -> Arrays.asList(value.split(" ")).iterator());
        //stringJavaRDD.filter(s -> !(s.length()==1)).foreach(value-> System.out.println(value));

    }

    private String getDeptName(String str) {
        String[] strings = str.split("/");
        return strings[strings.length - 2];
    }
}
