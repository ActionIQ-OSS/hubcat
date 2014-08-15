import SonatypeKeys._

// Import default settings. This changes `publishTo` settings to use the Sonatype repository and add several commands for publishing.
sonatypeSettings

//organization := "me.lessis"
organization := "com.nitayjoffe.thirdparty.me.lessis"

name := "hubcat"

version := "0.2.0-f74173eb"

description := "a vvip client of the github enterprises"

libraryDependencies ++= Seq("net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.0")

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

// needed for java test options
fork in Test := true

// passing env vars to tests is improved in sbt 0.13.0
javaOptions in Test := Seq("GHUSER", "GHPASS").map(v => "-D%s=%s".format(v, System.getenv(v)))

if (sys.env.getOrElse("TRAVIS", "false").toBoolean) {
  println("using travis")
  seq(ivyLoggingLevel := UpdateLogging.Quiet,
      logLevel in Global := Level.Warn,
      logLevel in Compile := Level.Warn,
      logLevel in Test := Level.Info)
} else seq()

crossScalaVersions := Seq("2.9.3", "2.10.2")

scalaVersion := "2.10.2"

scalacOptions := Seq(Opts.compile.deprecation)

licenses := Seq(
  "MIT" ->
  url("https://github.com/softprops/%s/blob/%s/LICENSE" format(name.value, version.value)))

homepage :=
  Some(url("https://github.com/softprops/%s/" format(name.value)))

publishArtifact in Test := false

publishMavenStyle := true

seq(bintraySettings:_*)

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("github", "gist")

seq(lsSettings:_*)

LsKeys.tags in LsKeys.lsync := Seq("github", "gist")

seq(buildInfoSettings:_*)

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](version)

buildInfoPackage := "hubcat"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := {
  <scm>
    <connection>scm:git:github.com/softprops/hubcat</connection>
    <developerConnection>scm:git:git@github.com:softprops/hubcat.git</developerConnection>
    <url>github.com/softprops/hubcat</url>
  </scm>
  <developers>
    <developer>
    </developer>
  </developers>
}
