package ru.aryukov.sbertechtestcase;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scala.Tuple2;

@SpringBootApplication
public class SbertechtestcaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbertechtestcaseApplication.class, args);

        List<Integer> inputDate = new ArrayList<>();
        inputDate.add(35);
        inputDate.add(12);
        inputDate.add(90);
        inputDate.add(20);

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<Integer> originalInteger = sparkContext.parallelize(inputDate);
        JavaRDD<Tuple2<Integer,Double>> valueSqrt = originalInteger.map(value -> new Tuple2<>(value, Math.sqrt(value)));

        System.out.println("Try to start logg");
        System.out.println(valueSqrt.toString());
        System.out.println("Try to end logg");
        sparkContext.close();
    }

}
