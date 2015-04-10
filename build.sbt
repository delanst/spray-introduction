name := "spray-introduction"

version := "1.0"

scalaVersion := "2.11.6"

resolvers += "spray repo" at "http://repo.spray.io"

val sprayVersion = "1.3.1"

libraryDependencies ++= Seq(
  /* spray libraries */
  "io.spray" %% "spray-routing" % sprayVersion ,
  "io.spray" %% "spray-client" % sprayVersion ,
  "io.spray" %% "spray-testkit" % sprayVersion % "test" ,

  /* json library */
  "org.json4s" %% "json4s-native" % "3.2.10" ,

  /* akka libraries */
  "com.typesafe.akka" %% "akka-actor" % "2.3.6" ,
  "com.typesafe.akka" %% "akka-http-experimental" % "0.7" ,

  /* logging and testing libraries */
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2" ,
  "org.scalatest" %% "scalatest" % "2.2.2" % "test" ,
  "org.mockito" % "mockito-all" % "1.9.5" % "test")