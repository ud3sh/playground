package org.apache.spark.streaming

import java.io.{NotSerializableException, ObjectOutputStream}

import org.apache.spark.rdd.{UnionRDD, RDD}
import org.apache.spark.streaming.dstream.InputDStream

import scala.collection.mutable.{ArrayBuffer, Queue}
import scala.reflect.ClassTag

//Copied from QueueInputStream
class CustomQueueInputStream[T: ClassTag](
                                           @transient ssc: StreamingContext,
                                           val queue: Queue[RDD[T]],
                                           oneAtATime: Boolean,
                                            defaultRDD: RDD[T]
                                         ) extends InputDStream[T](ssc) {

  override def start() { }

  override def stop() { }

  private def writeObject(oos: ObjectOutputStream): Unit = {
    //throw new NotSerializableException("queueStream doesn't support checkpointing")
  }

  override def compute(validTime: Time): Option[RDD[T]] = {
    val buffer = new ArrayBuffer[RDD[T]]()
    if (oneAtATime && queue.size > 0) {
      buffer += queue.dequeue()
    } else {
      buffer ++= queue.dequeueAll(_ => true)
    }
    if (buffer.size > 0) {
      if (oneAtATime) {
        Some(buffer.head)
      } else {
        Some(new UnionRDD(ssc.sc, buffer.toSeq))
      }
    } else if (defaultRDD != null) {
      Some(defaultRDD)
    } else {
      None
    }
  }

}