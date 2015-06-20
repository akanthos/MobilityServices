import play.Project._

name := "WhoElse"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.2.2",
  "org.webjars" % "bootstrap" % "3.3.4")

libraryDependencies ++= Seq(
  javaJdbc,
  "org.xerial" % "sqlite-jdbc" % "3.8.6",
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final" // replace by your jpa implementation
)

playJavaSettings
