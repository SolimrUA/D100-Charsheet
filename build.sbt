name := "d100-charsheet"

version := "0.1"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "org.scalafx" % "scalafx_2.12" % "8.0.102-R11"
)

mainClass in (Compile,run) := Some("controller.Controller")