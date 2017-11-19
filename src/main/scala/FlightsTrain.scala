import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.SparkSession


object FlightsTrain extends App{


  val spark = SparkSession.builder()
    .appName("FlightData")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  val rawData = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .text("file:///usr/dataset/train_noheader.tsv")

  rawData.show(1,false)

  rawData.foreach(row=>{
    val lable = row.toString().split("\t")
    println(lable)
  })

}
