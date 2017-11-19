/**
  * Created by qiang on 17-6-1.
  */

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.ml.attribute.NominalAttribute
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType,StructField,StringType}
import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.ALS

object FlightData {


  def startWorking()= {

    def getMinuteOfDay(depTime: String): Int = (depTime.toInt / 100).toInt * 60 + (depTime.toInt % 100)

    //define schema for raw data
    case class Flight(Month: String, DayofMonth:
    String, DayOfWeek: String, DepTime: Int,
                      CRSDepTime: Int, ArrTime: Int, CRSArrTime: Int,
                      UniqueCarrier: String, ActualElapsedTime: Int,
                      CRSElapsedTime: Int, AirTime: Int, ArrDelay: Double,
                      DepDelay: Int, Origin: String, Distance: Int)

    //ini spark config
    val conf = new SparkConf().setAppName("FlightData").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val flight2007 = sc.textFile("/usr/sparkwork/data/2007.csv")
    val header = flight2007.first
    val trainingData = flight2007
      .filter(x => x != header)
      .map(x => x.split(","))
      .filter(x => x(21) == "0")
      .filter(x => x(17) == "ORD")
      .filter(x => x(14) != "NA")
      .map(p => Flight(p(1), p(2), p(3), getMinuteOfDay(p(4)), getMinuteOfDay(p(5)), getMinuteOfDay(p(6)), getMinuteOfDay(p(7)), p(8), p(11).toInt, p(12).toInt, p(13).toInt, p(14).toDouble, p(15).toInt, p(16), p(18).toInt))

    trainingData.cache()


    trainingData.foreach(x=>println(x.DepDelay))

    val flight2008 = sc.textFile("/usr/sparkwork/data/2008.csv")
    val testingData = flight2008
      .filter(x => x != header)
      .map(x => x.split(","))
      .filter(x => x(21) == "0")
      .filter(x => x(17) == "ORD")
      .filter(x => x(14) != "NA")
      .map(p => Flight(p(1), p(2), p(3), getMinuteOfDay(p(4)), getMinuteOfDay(p(5)), getMinuteOfDay(p(6)), getMinuteOfDay(p(7)), p(8), p(11).toInt, p(12).toInt, p(13).toInt, p(14).toDouble, p(15).toInt, p(16), p(18).toInt))

    //testingData.cache
  }

}
