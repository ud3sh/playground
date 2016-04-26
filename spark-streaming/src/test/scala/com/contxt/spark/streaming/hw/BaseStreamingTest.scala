package com.contxt.spark.streaming.hw

import java.nio.file.Files

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming._
import org.apache.spark.{SparkConf, SparkContext}
import org.specs2.execute.{Result, AsResult}
import org.specs2.specification.AroundEach

import scala.collection.mutable
import scala.reflect.ClassTag


/**
  * This is a specs2 variation of the scala test in
  * https://github.com/mkuthan/example-spark/blob/master/src/test/scala/org/mkuthan/spark/SparkStreamingSpec.scala
  * See: https://github.com/mkuthan/example-spark
  */
trait BaseStreamingTest extends AroundEach {

  private val Master = "local[2]"
  private val AppName = "WordCountingTestApp"
  private val batchDuration = Seconds(1)

  private var sc: SparkContext = _
  private var ssc: StreamingContext = _


  override def around[R: AsResult](r: => R): Result = {
    createStreamingContext()
    try AsResult(r)
    finally {
      stopSparkContext()
      //new File(CheckpointDir).delete()
      //new File(CheckpointDir).delete()
    }
  }

  def createStreamingContext() {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)

    sparkConfig.foreach { case (k, v) => conf.setIfMissing(k, v) }

    sc = new SparkContext(conf)

    ssc = new StreamingContext(sc, batchDuration)
    ssc.checkpoint(checkpointDir)
  }

  def stopSparkContext() {
    if (ssc != null) {
      ssc.stop(stopSparkContext = false, stopGracefully = false)
      ssc = null
    }
    if (sc != null) {
      sc.stop()
      sc = null
    }
  }

  def sparkContext = sc
  def streamingContext = ssc
  def sparkConfig: Map[String, String] = Map("spark.streaming.clock" -> "org.apache.spark.streaming.util.ManualClock")
  def advanceClock(timeToAdd: Duration): Unit = {
    ClockWrapper.advance(ssc, timeToAdd)
  }
  def advanceClockOneBatch(): Unit = {
    advanceClock(Duration(batchDuration.milliseconds))
  }
  def checkpointDir: String = Files.createTempDirectory(this.getClass.getSimpleName).toUri.toString

  def createCheckpointlessQueueStream[T:ClassTag](streamingContext:StreamingContext,
                                                  queue: mutable.Queue[RDD[T]]): CustomQueueInputStream[T] = {
    new CustomQueueInputStream(streamingContext,
      queue,
      true,
      sparkContext.makeRDD(Seq[T](), 1))
  }

}
