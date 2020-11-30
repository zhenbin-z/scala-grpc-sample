lazy val root = (project in file("."))
  .settings(
    organization := "com.zhenbin",
    name := "grpc-server",
    version := "1.0",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
    ),
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value / "scalapb"
    ),
    PB.protoSources in Compile += baseDirectory.value / "src/main/scala/com/zhenbin/app/proto"
  )
