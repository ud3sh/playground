package com.contxt.spark.streaming.hw.producer

import java.io.InputStream
import java.nio.ByteBuffer

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.kinesis.AmazonKinesisClient
import com.amazonaws.services.kinesis.model.PutRecordRequest

import scala.io.Source

/**
 *
 * PRE: AWS credentials must be set, either using `aws configure` or the environment variables (AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)
 * @see DefaultAWSCredentialsProviderChain for more information
 *
 */
object DummyKinesisEventProducer {

  def main(args: Array[String]) {

    val streamName = System.getProperty("kinesis.stream.name", "Lines")
    val endPoint = System.getProperty("kinesis.end.point", "kinesis.us-east-1.amazonaws.com")

    val kinesisClient = new AmazonKinesisClient(new DefaultAWSCredentialsProviderChain())
    kinesisClient.setEndpoint(endPoint)

    val inputStream : InputStream = getClass.getResourceAsStream("/sample-input-homers-oddessy.log")
    var lineNumber = 0;

    for(line <- Source.fromInputStream(inputStream).getLines().take(900)) {
      lineNumber = lineNumber + 1
      // Create a partitionKey based on recordNum
      val partitionKey = s"partitionKey-$lineNumber"
      // Create a PutRecordRequest with an Array[Byte] version of the data
      val putRecordRequest = new PutRecordRequest().withStreamName(streamName)
        .withPartitionKey(partitionKey)
        .withData(ByteBuffer.wrap(line.getBytes()))

      kinesisClient.putRecord(putRecordRequest)
    }

    inputStream.close()
  }
}