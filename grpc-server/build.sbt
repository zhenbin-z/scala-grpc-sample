val PureconfigVersion = "0.12.2"
val MacwireVersion = "2.3.3"
val Slf4jdkVersion = "1.7.30"
val CirceVersion = "0.13.0"
val CatsVersion = "2.1.1"
val DoobieVersion = "0.9.0"
val FlyWayVersion = "6.3.1"

/* flyway settings */
val DatabaseHost = sys.env.getOrElse("DATABASE_HOST", "127.0.0.1")
val DatabasePort = sys.env.getOrElse("DATABASE_PORT", 5432)
val DatabaseDB = sys.env.getOrElse("DATABASE_DB", "local_db")
val DatabaseUser = sys.env.getOrElse("DATABASE_USER", "local")
val DatabasePassword = sys.env.getOrElse("DATABASE_PASSWORD", "local")
val jdbcUrl =
  "jdbc:postgresql://" + DatabaseHost + ":" + DatabasePort + "/" + DatabaseDB

lazy val root = (project in file("."))
  .settings(
    organization := "com.zhenbin",
    name := "grpc-server",
    version := "1.0",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "com.github.pureconfig" %% "pureconfig" % PureconfigVersion,
      "com.softwaremill.macwire" %% "macros" % MacwireVersion % "provided",
      "org.slf4j" % "slf4j-jdk14" % Slf4jdkVersion,
      "org.typelevel" %% "cats-core" % CatsVersion,
      "org.typelevel" %% "cats-effect" % CatsVersion,
      "org.tpolecat" %% "doobie-core" % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari" % DoobieVersion,
      "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
      "org.tpolecat" %% "doobie-refined" % DoobieVersion,
      "org.flywaydb" % "flyway-core" % FlyWayVersion,
      "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
    ),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    flywayUrl := jdbcUrl,
    flywayUser := DatabaseUser,
    flywayPassword := DatabasePassword,
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value / "scalapb"
    ),
    PB.protoSources in Compile += baseDirectory.value / "src/main/scala/com/zhenbin/app/proto"
  )
  .enablePlugins(FlywayPlugin)
