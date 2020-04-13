# SberTech Testcase

Apllication for handling a lot of *.json files in different folders
# REST API

The REST API to the make sparkjob.

### Request

`POST http://localhost:8080/getsparkjob`

{
  "pathToJson":"src/main/resources",
  
  "sparkSqlWarehouseDir":"C:/MyJavaCode/SberTech/spark-warehouse",
  
  "outputPath":"C:/MyJavaCode/SberTech/src/main/resources/test1",
  
  "hadoopHomeDir":"C:/MyJavaCode/hadooponwindows-master/hadooponwindows-master"
}

### Request field description 

`pathToJson` - it path to folder which contains folder with our jsons

`sparkSqlWarehouseDir` - define parameter `spark.sql.warehouse.dir`

`hadoopHomeDir` - define parameter `hadoop.home.dir`

`outputPath` - define output for result file locations
### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    {"employWithMaxSalary":"Diego Maradona","salarySum":1050}
