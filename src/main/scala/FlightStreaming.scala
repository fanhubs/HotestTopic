import org.apache.spark.sql.SparkSession
/**
  * Created by qiang on 17-8-6.
  */
object FlightStreaming extends App {

  val spark = SparkSession.builder()
    .appName("spark basic example")
    .master("local[20]")
    .config("spark.sql.shuffle.partitions", 6)
    .config("spark.executor.memory", "3g")
    .getOrCreate()

  //
  val topics = "flightData"
  val logLinesDStream = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:2181")
    .option("subscribe", topics)
    .load()

}
