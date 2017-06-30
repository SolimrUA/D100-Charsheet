name := "d100-charsheet"

version := "0.1"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.102-R11",
  "org.scala-lang" % "scala-reflect" % "2.12.2"
)

mainClass in (Compile,run) := Some("controller.Controller")