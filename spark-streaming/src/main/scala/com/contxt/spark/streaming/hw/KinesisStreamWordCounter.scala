package com.contxt.spark.streaming.hw

import java.util.Properties

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream.TRIM_HORIZON
import org.apache.spark.Logging
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK_2
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}
import org.apache.spark.streaming.kinesis._

object KinesisStreamWordCounter extends Logging{

  /**
   * @param args[0] - system properties file (see hw-spark-conf.properties)
   */
  def main(args: Array[String]) {
    WordCountStreamProcessor.start(args, createStream, extractWordStream)
  }

  def createStream (streamingContext: StreamingContext, options:Properties):  ReceiverInputDStream[Array[Byte]] = {

    val kinesisStream = KinesisUtils.createStream(
                            streamingContext,
                            options.getProperty("kinesis.app.name", "KinesisStreamWordCounter"), //kinesis app name (unique per account, and region)
                            options.getProperty("kinesis.stream.name", "Lines"), //kinesis stream name
                            options.getProperty("kinesis.end.point", "kinesis.us-east-1.amazonaws.com"),  //end point url http://docs.aws.amazon.com/general/latest/gr/rande.html#ak_region
                            options.getProperty("kinesis.region", "us-east-1"),  //kinesis region name https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-regions-availability-zones.html
                            TRIM_HORIZON, //position of the queue to read from, either back or front
                            Duration(options.getProperty("window.length.millis", "2000").toInt), //checkpoint interval, ideally match the queue
                            MEMORY_AND_DISK_2)

    logInfo("Kinesis Stream Created")

    kinesisStream
  }

  def extractWordStream(stream: DStream[Array[Byte]]):DStream[String] = {
    logInfo("Extracting word stream")
    stream.map(v => new String(v)).flatMap(_.split(" "))
  }

}
