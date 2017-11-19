import sbt._
import Keys._
//import sbtassembly.Plugin._
//import AssemblyKeys._


version := "1.0"
name:="AireChinaFlight"
scalaVersion := "2.11.11"

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"
libraryDependencies +="org.apache.spark" %% "spark-mllib" % "2.2.0"
libraryDependencies +="org.apache.spark" %% "spark-streaming" % "2.2.0" % "provided"
libraryDependencies +="org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.2.0" % "provided"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.6.5" % "provided"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "0.11.0.1" % "provided"
libraryDependencies += "net.cakesolutions" %% "scala-kafka-client" % "0.11.0.0" % "provided"



  //.map(
//_.excludeAll(ExclusionRule(organization = "org.mortbay.jetty"))
//)


/**mergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties"         => MergeStrategy.first
  case "org/apache/spark/unused/UnusedStubClass.class" => MergeStrategy.first
  case "META-INF/maven/com.google.guava/guava/pom.properties"  => MergeStrategy.first
  case "META-INF/maven/com.google.guava/guava/pom.xml" => MergeStrategy.first
  case "META-INF/maven/org.apache.avro/avro-ipc/pom.properties" => MergeStrategy.last
  case "META-INF/maven/org.slf4j/slf4j-api/pom.properties" => MergeStrategy.first
  case "META-INF/maven/org.slf4j/slf4j-api/pom.xml" => MergeStrategy.first

  case x =>
    val oldStrategy = (mergeStrategy in assembly).value
    oldStrategy(x)
}**/
        