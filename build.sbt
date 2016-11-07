name          := "data.uniref"
organization  := "bio4j"
description   := "data.uniref project"

bucketSuffix  := "era7.com"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq (
  "org.scala-lang.modules" %% "scala-xml"           % "1.0.5",
  "org.scala-lang.modules" %% "scala-java8-compat"  % "0.8.0-RC3"
) ++ testDependencies

lazy val testDependencies = Seq (
  "org.scalatest"         %% "scalatest"    % "2.2.6"   % Test
)

// caused by scalatest
dependencyOverrides := Set (
  "org.scala-lang.modules" %% "scala-xml"     % "1.0.5"
)


wartremoverExcluded ++= Seq(
  baseDirectory.value/"src"/"main"/"scala"/"xml"/"parsers.scala"
)

testOptions       in Test += Tests.Argument("-oD")
parallelExecution in Test := false
