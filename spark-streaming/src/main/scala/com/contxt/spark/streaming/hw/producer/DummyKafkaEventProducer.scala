package com.contxt.spark.streaming.hw.producer

import java.io.InputStream
import java.util.Properties

import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}
import org.apache.spark.Logging

import scala.io.Source


object DummyKafkaEventProducer extends Logging{

  def main(args: Array[String]) {
    val props:Properties = new Properties();
    props.put("bootstrap.servers", System.getProperty("kafka.broker", "127.0.0.1:9092"));
    props.put("client.id", "KafkaWordCountEventProducer");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("block.on.buffer.full", "false");
    val kafkaProducer = new KafkaProducer[String, String](props);
    val topic = System.getProperty("kafka.topic", "Lines");
    val stream : InputStream = getClass.getResourceAsStream("/sample-input-homers-oddessy.log")

    var lineNumber = 0;
    for(line <- Source.fromInputStream(stream).getLines()){
      lineNumber = lineNumber + 1
      if (lineNumber < 25000){
        kafkaProducer.send(new ProducerRecord[String, String](topic, lineNumber.toString, line))
        log.debug("""Sending line "{}" to Kafka""", line);
      }
      if (lineNumber % 128 == 0){
        Thread sleep 100 //just slow down input for testing
      }
    }

    stream.close();
  }

}
