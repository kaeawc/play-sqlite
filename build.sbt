name := "play-sqlite"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  "joda-time" % "joda-time" % "2.3",
  "org.xerial" % "sqlite-jdbc" % "3.7.15-M1"
)

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions"
)

play.Project.playScalaSettings
