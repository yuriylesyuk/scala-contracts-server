name := "scala-contracts-server"

version := "0.1.0=SNAPSHOT"

organization := "org.casualmiracles"

scalaVersion := "2.11.2"

resolvers ++= Seq( "releases" at "http://oss.sonatype.org/content/repositories/releases" )					

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
	"com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
	"ch.qos.logback" % "logback-classic" % "1.0.9",
	"org.slf4j" % "slf4j-simple" % "1.6.4"
)

libraryDependencies ++= {
	val liftVersion = "2.6+"
	Seq(
		"net.liftweb" %% "lift-webkit" % liftVersion % "compile",
		"net.liftweb" %% "lift-json" % liftVersion % "compile"
	)
}

jetty()
