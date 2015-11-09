package com.contxt.spark.streaming.hw

import java.util.Properties

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}

/**
 * Prints out the word counts every n seconds when words are written to socket on port 9011 <br/>
 *
 * 1)Run This class <br>
 * 2)On command line run : <br>
 * <pre>nc -lk 9011</pre>
 * 3)Write words to the socket on terminal
 * 4)This app will count them and write the result to output or output.dir property in hw-spark-conf.properties (/tmp/word-count-app)
 *
 */
object SocketStreamWordCounter extends Logging {

  /**
   * @param args[0] - system properties file (see hw-spark-conf.properties)
   */
  def main (args: Array[String]) {
    WordCountStreamProcessor.start(args, createStream, extractWordStream)
  }

  def createStream(streamingContext: StreamingContext, options:Properties): ReceiverInputDStream[String] = {
    val socketStreamPort = options.getProperty("socket.streaming.port", "9011").toInt
    streamingContext.socketTextStream(options.getProperty("socket.streaming.host", "localhost"), socketStreamPort)
  }

  def extractWordStream(stream: DStream[String]):DStream[String] = {
    stream.flatMap(_.split(" "))
  }

}
