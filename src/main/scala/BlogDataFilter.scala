import java.io.{File, FileInputStream, FileReader}
import java.nio.file.Paths

import scala.io.Source
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

import scala.util.matching.Regex

/**
  * Created by qiang on 17-11-13.
  */
object BlogDataFilter extends App {

  //filter the meta data
  filterBlogsData

  /**
    * the dump of blogs data has some incorrect urls and pages.
    *
    * all of these incorrect urls should be remove from meta data.
    *
    */
  def filterBlogsData ={

    val spark = SparkSession.builder()
      .appName("blogsData")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val metaUrls = spark.read
      .option("header", "false")
      .option("inferSchema", "true")
      .text("file:///usr/dataset/cnblogs2.text")
      .filter(item=>item.toString().contains(".html")).map(row=>{
      var start = row.toString().indexOf("http")
      var end=row.toString().indexOf(".html")+5
      if(start>0 && end>0)
      row.toString().substring(start,end)
      else
        "null"
    }).toDF()

    //read webpage one by one
    metaUrls.foreach(path=>{
      //check if the page exists in the folder
      val localPath = path.getString(0).replace("http:/","/usr/dataset")
      println(localPath)
      val file = new File (localPath)
      if(file.isFile && file.exists()){
        println("===========// will start on file "+ localPath+" //=============")
        println(localPath)
        val pageContent=readPageContent(localPath)


        println(getBlogerName(localPath))

        println("//===========page count will be shown========")
        println(getCommentsCount(pageContent))
        println("//===========page count was shown========")




        //println(pageContent)
      }
      //reading the html page
      //parse the count items.

    })



  }


  /**
    * get blocker name from url
    * actually, the bloger name is the part of url
    * @param localPath
    * @return
    */
  def getBlogerName(localPath:String):String = {
    val startIndex = localPath.indexOf("www.cnblogs.com")+16
    val endIndex = localPath.indexOf("/",startIndex)


    var localp = "get bloger failed ---"+localPath;
    if(startIndex>=0&&endIndex>=0)
      localp=localPath.substring(startIndex, endIndex)

    localp
  }

  def getCommentsCount(content:String):String={

    var commentsCnt = "0"
    val startIndex = content.indexOf("stats-comment_count\">评论")+26
    val endIndex = content.indexOf("</span>",startIndex)
    if(startIndex>=0 && endIndex>=0)
      commentsCnt = content.substring(startIndex,endIndex)

    commentsCnt


  }

  def readPageContent(localPath:String):String={
    import scala.io.Source

    val source = Source.fromFile(localPath)
    source.getLines.toArray.mkString


  }


}
