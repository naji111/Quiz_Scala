// Define the name of the project
name := "ScalaQuizGame"

// Define the version of the project
version := "3.5.3"

// Specify the Scala version to be used
scalaVersion := "2.12.18"

// List of library dependencies required for the project
libraryDependencies ++= Seq(
  // Dependency for ScalaFX, which is used to create GUI applications in Scala
  "org.scalafx" %% "scalafx" % "21.0.0-R32",

  // Dependencies for the circe library, used for JSON parsing
  "io.circe" %% "circe-core" % "0.14.1",        // Core circe library
  "io.circe" %% "circe-generic" % "0.14.1",     // Circe generic derivation
  "io.circe" %% "circe-parser" % "0.14.1",      // Circe parser for JSON strings

  // Dependencies for JavaFX, which is used for building rich client applications
  "org.openjfx" % "javafx-base" % "17" classifier "win",        // Base JavaFX module for Windows
  "org.openjfx" % "javafx-controls" % "17" classifier "win",    // JavaFX controls module for Windows
  "org.openjfx" % "javafx-fxml" % "17" classifier "win",        // JavaFX FXML module for Windows
  "org.openjfx" % "javafx-graphics" % "17" classifier "win"     // JavaFX graphics module for Windows
)
