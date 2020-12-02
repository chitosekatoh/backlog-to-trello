import Dependencies._

version := "0.1"

scalaVersion := "2.13.3"

val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.3"
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test
libraryDependencies += "com.typesafe" % "config" % "1.3.1"