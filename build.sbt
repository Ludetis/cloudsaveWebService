name := """cloudsaveWebService"""

version := "2.3-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Sonatype Snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" %% "webjars-play" % "2.2.2",
  "org.webjars" % "jquery" % "2.1.0-2",
  "org.webjars" % "requirejs" % "2.1.11-1",
  jdbc,
  anorm,
  cache,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.0-SNAPSHOT" exclude("org.scala-stm", "scala-stm_2.10.0"),
  // Test dependencies
  "org.webjars" % "rjs" % "2.1.11-1-trireme" % "test",
  "org.webjars" % "squirejs" % "0.1.0" % "test", 
  "jp.t2v" %% "play2-auth"      % "0.12.0-SNAPSHOT",
  "jp.t2v" %% "play2-auth-test" % "0.12.0-SNAPSHOT" % "test",
  "com.fasterxml.jackson.module" % "jackson-module-scala" % "2.1.2",
   "reactive_mongo_plugin" %% "reactive_mongo_plugin" % "0.0.27"
)

// JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

MochaKeys.requires += "./Setup"
play.Project.playScalaSettings
