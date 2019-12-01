ThisBuild / scalaVersion := "2.13.1"
ThisBuild / organization := "lt.dzebleckis"
ThisBuild / scalacOptions ++= Seq("-deprecation", "-Xlint")


val scalaTest = "org.scalatest" %% "scalatest" % "3.1.0"

lazy val aoc = (project in file("."))
  .aggregate(aoc2018, aoc2019)
  .dependsOn(aoc2018, aoc2019)
  .settings(
    name := "Advent of code",
  )

lazy val aoc2018 = (project in file("2018"))  

lazy val aoc2019 = (project in file("2019"))
  .settings(
    name := "Advent of code 2019",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.1.0",
      scalaTest % Test
    ),
  )
  
