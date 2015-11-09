==DESCRIPTION==
This contains three simple word counting apps, SocketStreamWordCounter, KafkaStreamWordCounter, and KinesisStreamWordCounter
They could all run as a standalone or in a spark cluster.

The three streaming apps essentially use the same streaming logic in WordCountStreamProcessor.  As indicated by the app name, the input stream varies in each of the actually app

hw-spark-conf.properties contains all default properties (common and app specific)

==BUILDING==
Run the following from the project root
sbt clean package assembly

--RUNNING STANDALONE--

1)SocketStreamWordCounter
Standalone:
Run SocketStreamWordCounter via the command line (with the properties file as an optional argument)

2)KafkaStreamWordCounter

==DEPLOYING==

==NOTES==
Output is dumped after each window to output.dir set in hw-spark-conf.properties (/tmp/word-count-app by default).
Total counts are dumped to total-* directories after each window
The window counts are dumped to window-* directories after each window

There will be LOTS of output and output.dir if the app is run for a while.
