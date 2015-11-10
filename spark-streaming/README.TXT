==DESCRIPTION==
This contains three simple word counting apps, SocketStreamWordCounter, KafkaStreamWordCounter, and KinesisStreamWordCounter
They could all run as a standalone or in a spark cluster.

The three streaming apps essentially use the same streaming logic in WordCountStreamProcessor.  As indicated by the app name, the input stream varies in each of the actually app

hw-spark-conf.properties contains all default properties (common and app specific)

==BUILDING==
Run the following from the project root
sbt clean package assembly

--RUNNING STANDALONE--
Demoing each app consists of writing events to the input source, and either running the app as a standalone java class or submitting it as a spark job
hw-spark-conf.properties may need to be updated

1)SocketStreamWordCounter

Event Producer:
write to port 9011 (see SocketStreamWordCounter for more info) on machine specificied by socket.streaming.host in hw-spark.conf.properties

Streaming App:
java -cp <jar-file-from-build-step> com.contxt.spark.streaming.hw.SocketStreamWordCounter

2)KafkaStreamWordCounter

Event Producer : 
java -cp <jar-file-from-build-step> com.contxt.spark.streaming.hw.producer.DummyKafkaEventProducer

Streaming App  : 
java -cp <jar-file-from-build-step> com.contxt.spark.streaming.hw.KafkaStreamWordCounter


3)KinesisStreamWordCounter

Event Producer:
java -cp <jar-file-from-build-step> com.contxt.spark.streaming.hw.producer.DummyKinesisEventProducer

Streaming App :
java -cp <jar-file-from-build-step> com.contxt.spark.streaming.hw.KinesisStreamWordCounter

==DEPLOYING==
Run the above streaming app classes as spark job

==NOTES==
Output is dumped after each window to output.dir set in hw-spark-conf.properties (/tmp/word-count-app by default).
Total counts are dumped to total-* directories after each window
The window counts are dumped to window-* directories after each window

There will be LOTS of output and output.dir if the app is run for a while.
