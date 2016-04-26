package com.contxt.spark.streaming.hw

import java.util.Properties

import org.apache.spark.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}
import org.apache.spark.streaming.kafka.KafkaUtils


object KafkaStreamWordCounter extends Logging {

  /**
   * @param args[0] - system properties file (see hw-spark-conf.properties)
   */
  def main(args: Array[String]) {
    WordCountStreamProcessor.start(args, createStream, extractWordStream)
  }

  def createStream (streamingContext: StreamingContext, options:Properties): ReceiverInputDStream[(String, String)] = {

    val kafkaStream = KafkaUtils.createStream(
      streamingContext,
      options.getProperty("zookeeper.quorum", "127.0.0.1:2181"),
      options.getProperty("kafka.group.id", "kafkaStreamWordCounter"),
      Map((options.getProperty("kafka.topic", "Lines"), 1)),
      StorageLevel.MEMORY_AND_DISK_SER)

    logInfo("Kafka Stream Created")

    kafkaStream
  }

  def extractWordStream(stream: DStream[Pair[String, String]]):DStream[String] = {
    logInfo("Extracting word stream")
    val lineStream:DStream[String] = stream.map(v => v._2)
    lineStream.flatMap(_.split(" "))
  }

}
