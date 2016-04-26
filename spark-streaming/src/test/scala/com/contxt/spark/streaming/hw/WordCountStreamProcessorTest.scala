package com.contxt.spark.streaming.hw

import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream
import org.specs2.mutable._

import scala.collection.mutable.ListBuffer
import scala.collection.mutable

import org.apache.spark.rdd.RDD


class WordCountStreamProcessorTest extends Specification with BaseStreamingTest {

  "Intial set" should {
    "be counted" >> {

      val lines = mutable.Queue[RDD[String]]()
      var windowResults = ListBuffer.empty[(String, Int)]
      var totalResults = ListBuffer.empty[(String, Int)]

      WordCountStreamProcessor
        .processStream2(createCheckpointlessQueueStream(streamingContext, lines),
                        extractWordStream)(
                                           (stream: DStream[(String, Int)]) => extracted(totalResults, stream),
                                           (stream: DStream[(String, Int)]) => extracted(windowResults, stream))

      streamingContext.start();

      advanceClock(Seconds(10))

      lines += sparkContext.makeRDD(Seq("hello", "hello world"))
      lines += sparkContext.makeRDD(Seq("hi", "hello world"))


      //totalResults.toList.groupBy(p => p._1).map {case (k, v) => (k, v.last._2)} must beEqualTo(Map("world"->2, "hi"->1, "hello" -> 3)).eventually


      totalResults.toList.groupBy(p => p._1).map {case (k, v) => (k, v.last._2)}.size must beEqualTo(3).eventually

      val counts = totalResults.toList.groupBy(p => p._1).map {case (k, v) => (k, v.last._2)};
      counts("world") must beEqualTo(2)
      counts("hello") must beEqualTo(3)
      counts("hi") must beEqualTo(1)


      advanceClock(Seconds(10))
      lines += sparkContext.makeRDD(Seq("hello", "good bye now"))
      advanceClock(Seconds(10))
      totalResults.toList.groupBy(p => p._1).map {case (k, v) => (k, v.last._2)}.size must beEqualTo(6).eventually
      //val counts2 = totalResults.toList.groupBy(p => p._1).map {case (k, v) => (k, v.last._2)};

    }

  }

  def extractWordStream(stream: DStream[String]):DStream[String] = {
    stream.flatMap(_.split(" "))
  }

  def extracted(totalResults: ListBuffer[(String, Int)], stream: DStream[(String, Int)]): Unit = {
    stream.foreachRDD((rdd: RDD[(String, Int)]) => {
      totalResults.clear()
      totalResults ++= rdd.collect().sorted
    })
  }

}
