name := "brp-charsheet"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  //"junit" % "junit" % "4.10" % "test",
  //"org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "org.scalafx" % "scalafx_2.12" % "8.0.102-R11"
)

mainClass in (Compile,run) := Some("view.TextPrint")