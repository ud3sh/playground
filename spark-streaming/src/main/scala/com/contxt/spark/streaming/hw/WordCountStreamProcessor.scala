package com.contxt.spark.streaming.hw

import java.io.{FileWriter, BufferedWriter, File, FileReader}
import java.util.Properties

import org.apache.spark.{Logging, SparkConf}
import org.apache.spark.streaming.{StreamingContextState, Milliseconds, StreamingContext}
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}

/**
 * Tracks the word count in a DStream
 */
object WordCountStreamProcessor extends Logging {

  type StreamCreationFunction[T] = (StreamingContext, Properties) => ReceiverInputDStream[T]
  type ExtractWordStreamFunction[T] = (DStream[T])=>DStream[String]

  def start[T](commandLineArgs:Array[String],
               streamCreationFunction: StreamCreationFunction[T],
               wordStreamExtractionFunction: ExtractWordStreamFunction[T]) = {

    log.info("Starting SocketStreamWordCount App")

    val options = new Properties()

    if (commandLineArgs.size > 0){
      options.load(new FileReader(commandLineArgs(0)))
    }

    val conf = new SparkConf().setMaster(options.getProperty("spark.master.override", System.getProperty("spark.master", "local[2]")))
                              .setAppName("WordCountingApp")
                              .set("spark.streaming.stopGracefullyOnShutdown",
                                   options.getProperty("spark.streaming.stopGracefullyOnShutdown","true"))

    val streamingWindowMillis = options.getProperty("window.length.millis", "2000").toInt
    val streamingContext = new StreamingContext(conf, Milliseconds(streamingWindowMillis))
    streamingContext.checkpoint(options.getProperty("spark.checkpoint.dir", "/tmp/spark")) //required to update state

    val stream = streamCreationFunction(streamingContext, options);
    val outputDir = options.getProperty("output.dir", "/tmp/word-count-app")

    val count = processStream(stream, wordStreamExtractionFunction) match {
      case (globalTotal, windowTotal) =>{
        globalTotal.cache()
        globalTotal.saveAsTextFiles(outputDir + "/total")
        globalTotal.print()
        windowTotal.saveAsTextFiles(outputDir + "/window")
        windowTotal.print()
      }
    }

    streamingContext.start()
    streamingContext.awaitTermination();

    log.info ("Streaming terminated, attempting to gracefully shutdown ..")
    terminate(streamingContext, stream, outputDir);

  }

  protected def processStream[T](stream: DStream[T], lineExtractionFunction: ExtractWordStreamFunction[T]) = {

    val words = lineExtractionFunction(stream)
    val pairs = words.map(word => (word.toLowerCase(), 1))

    val windowWordCounts = pairs.reduceByKey(_ + _)
    val totalWordCounts = pairs.updateStateByKey(updateFunction)

    (totalWordCounts, windowWordCounts)
  }

  def updateFunction(newOccurences: Seq[Int], state:Option[Int]) = {
     Some(state.getOrElse(0) + newOccurences.size)
  }

  private def terminate(streamingContext:StreamingContext,
                        inputStream:ReceiverInputDStream[_],
                        outputDir:String): Unit = {
    val sb: StringBuffer = new StringBuffer();
    sb.append("See latest " + outputDir + "/total-* directory for the word counts.\n");
    if (streamingContext.getState() == StreamingContextState.ACTIVE){
      log.info ("Streaming context is active.  Stopping...")
      sb.append("Streaming context is active.  Stopping...\n")
      writeToFile(outputDir + "/result-with-sc-active." + System.currentTimeMillis().toString + ".log", sb.toString);
      streamingContext.stop(true, true);
    } else {
      log.info ("Streaming context has been shut down by spark.  No need to stop it")
      sb.append("Streaming context has been shut down by spark.  No need to stop it\n")
      writeToFile(outputDir + "/result." + System.currentTimeMillis().toString + ".log", sb.toString);
    }
  }

  private def writeToFile(path:String, msg:String) {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(msg)
    bw.close()
  }
}
