
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.scalalang.typed.{avg=>typedAvg,count => typedCount, sum => typedSum}
import java.io.File

/**
  * Created by qiang on 17-8-5.
  */
object FlightDf extends App {

  var timeStart = java.time.LocalDate.now();

  val spark = SparkSession.builder()
    .appName("FlightData")
    .master("local[*]")
    //.config("spark.sql.shuffle.partitions", 1)
    //.config("spark.executor.memory", "500m")
    .getOrCreate()


  import spark.implicits._

  val df = spark.read
    .option("header","true")
    .option("inferSchema","true")
    .csv("hdfs://Qiang-Think:54310/input/")


  case class FlightsTimelyData (flightNum:String, arrDelay:String, depDelay:String)

  df.createOrReplaceTempView("flights")
  val ds = spark.sql("SELECT FlightNum,ArrDelay,DepDelay FROM flights ")
    .as[FlightsTimelyData]

  //ds.filter(flit=>flit.depDelay=="NA").show()
  //ds.reduc


  ds.filter(flit=>flit.depDelay.trim!="NA")
    .filter(flit=>flit.arrDelay.trim!="NA")
    .groupByKey(flt=>flt.flightNum)
    .agg(typedAvg[FlightsTimelyData](_.arrDelay.toInt)
    .name("avg(arrDelay)"))
    .orderBy("value").write.csv("hdfs://Qiang-Think:54310/output/avg-data"+Math.random())


  //ds.show()

  //sqlDF.show()
  //df.show()
  //print the csv schema..
  //df.printSchema()
  //only show first 5000 rows.
  //df.show(7453000,false)

  //print total data rows.
  //println("the total number of flight data: "+ df.count())




}
