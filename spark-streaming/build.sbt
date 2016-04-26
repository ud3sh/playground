
name := "spark-streaming"
version := "1.0"
scalaVersion := "2.10.6"
organization := "com.contxt"
resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  Resolver.mavenLocal
)
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.1" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.5.0" % "provided",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.1",
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % "1.6.1",
  "org.specs2" %% "specs2-core" % "3.6.1" % "test",
  "junit" % "junit" % "4.8.1"
)
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
}