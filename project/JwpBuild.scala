import sbt._
import Keys._

object JwpBuild extends Build {

  type Settings = Seq[Setting[_]]

  def project(id: String, base: String)(settings: Settings = Seq()): Project = {
    Project(id = id, base = file(base)).settings(settings.flatten: _*)
  }

  lazy val root = project("root", ".")() dependsOn (jwp, jwpJatl, jwpJsoup)

  lazy val jwp = project("jwp", "jwp") {
    globalSettings
  }

  lazy val jwpJatl = project("jwp-jatl", "jatl") {
    globalSettings ++ Seq(
      libraryDependencies += "com.googlecode.jatl" % "jatl" % "0.2.2"
    )
  }
  
  lazy val jwpJsoup = project("jwp-jsoup", "jsoup") {
    globalSettings ++ Seq(
      libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"
    )
  }

  lazy val globalSettings: Settings = Seq(
    version := "1.0-SNAPSHOT",
    organization := "org.codeswarm",
    organizationHomepage := Some(url("https://github.com/codeswarm")),
    homepage := Some(url("https://github.com/codeswarm/jwp")),
    licenses := Seq(
      "The Apache Software License, Version 2.0" ->
      url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      Some(
      if (v.trim.endsWith("SNAPSHOT"))
        "snapshots" at nexus + "content/repositories/snapshots"
      else
        "releases" at nexus + "service/local/staging/deploy/maven2"
      )
    },
    pomExtra := {
      val org = "codeswarm"
      val repo = "jwp"
      <scm>
        <url>https://github.com/{org}/{repo}</url>
        <connection>scm:git:git://github.com/{org}/{repo}.git</connection>
        <developerConnection>scm:git:ssh://git@github.com/{org}/{repo}.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>chris-martin</id>
          <name>Chris Martin</name>
          <url>https://github.com/chris-martin</url>
        </developer>
      </developers>
    },
    scalaVersion := "2.10.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  )

}
