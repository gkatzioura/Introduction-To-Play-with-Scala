name := """PlayJdbc"""
organization := "com.gkatzioura.play"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test

libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += json

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.gkatzioura.play.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.gkatzioura.play.binders._"
