package org.apache.spark.streaming

import org.apache.spark.util.ManualClock

/**
  * Copied from: https://github.com/mkuthan/example-spark/blob/master/src/test/scala/org/apache/spark/ClockWrapper.scala
  * See https://github.com/mkuthan/example-spark
  */
object ClockWrapper {
  def advance(ssc: StreamingContext, timeToAdd: Duration): Unit = {
    val manualClock = ssc.scheduler.clock.asInstanceOf[ManualClock]
    manualClock.advance(timeToAdd.milliseconds)
  }
}