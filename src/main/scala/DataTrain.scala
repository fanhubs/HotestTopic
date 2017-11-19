/**
  * Created by qiang on 17-10-29.
  */

import scala.collection._

object DataTrain extends App{


  val digits=List(4,2)

  val nother = 9::4::2::5::Nil

  val t =nother :+ 10



//  class Prefixer(val prefix :String)
//
//  def addPerfix(s:String)(implicit p:Prefixer)  =  println(p.prefix +s)
//
//  implicit val myImplicitPrefixer = new Prefixer("***")
//  addPerfix("abc")



  import java.io.File

  import scala.io.Source

  class RichFile(val file:File){
    def read = Source.fromFile(file.getPath()).mkString

    def readBook =  Source.fromFile(file.getPath()).mkString
  }

  object Context{
    implicit def file2RichFile(f:File)= new RichFile(f)
  }

  object ImplictDemo {

    def main(args: Array[String]) {
      import Context.file2RichFile
      println(new File("f:\\create.sql").readBook)
    }

  }

}
