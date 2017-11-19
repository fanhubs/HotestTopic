import cakesolutions.kafka.KafkaProducer

import cakesolutions.kafka.KafkaProducer.Conf
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.common.serialization.{StringDeserializer,StringSerializer}
/**
  * Created by qiang on 17-9-17.
  */
object ConsumerToProducerBoot extends App {
  val config = ConfigFactory.load()
  FlightSource(config.getConfig("consumer"), config.getConfig("producer"))
}

object FlightSource {

  def apply(consumerConfig: Config, producerConfig: Config) = {


  }


  def getData()={

    val conf = ConfigFactory.load

    // Create a org.apache.kafka.clients.producer.KafkaProducer from properties defined in a Typesafe Config file
    val producer = KafkaProducer(
      Conf(conf, new StringSerializer(), new StringSerializer())
    )
    //producer.send()

  }

}
