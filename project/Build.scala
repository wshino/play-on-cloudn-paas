import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "play-on-cloudn-paas"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.cloudfoundry" % "auto-reconfiguration" % "0.6.6",
    "mysql" % "mysql-connector-java" % "5.1.29",
    "com.typesafe.slick" %% "slick" % "2.0.0"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    resolvers += "Spring Milestone Repository" at "http://maven.springframework.org/milestone"
  )

}
