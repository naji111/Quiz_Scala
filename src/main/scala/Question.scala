// Import necessary classes and methods from the circe library for JSON parsing
import io.circe._
import io.circe.parser._
import io.circe.generic.semiauto._
import scala.io.Source

// Define a case class to represent a question
case class Question(question: String, choices: List[String], correctAnswer: String)

// Define a companion object for the Question case class
object Question {

  // Create an implicit JSON decoder for the Question case class
  implicit val questionDecoder: Decoder[Question] = deriveDecoder[Question]

  // Define a method to load questions from a JSON file
  def loadQuestions(filename: String): List[Question] = {
    // Read the content of the JSON file into a string
    val source = Source.fromFile(filename)
    val jsonString = try source.mkString finally source.close()

    // Parse the JSON string into a list of Question objects
    decode[List[Question]](jsonString) match {
      case Right(questions) => questions // Return the list of questions if parsing is successful
      case Left(error) => throw new Exception(s"Failed to parse questions: $error") // Throw an exception if parsing fails
    }
  }
}
