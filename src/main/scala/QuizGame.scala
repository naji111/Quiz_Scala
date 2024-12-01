// Import necessary classes from the scalafx library to create the GUI application
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.text.Font

// The main object that extends JFXApp to create a ScalaFX application
object QuizGame extends JFXApp {

  // Variables to hold the list of questions, the current question index, and the user's score
  var questions: List[Question] = List()
  var currentQuestionIndex = 0
  var score = 0

  // UI elements for displaying the quiz
  val questionLabel = new Label("") {
    font = Font.font(24) // Set the font size of the question label to 24
  }
  val choicesBox = new VBox {
    spacing = 20 // Set the spacing between choice buttons to 20
  }
  val scoreLabel = new Label("Score: 0/0") {
    font = Font.font(18) // Set the font size of the score label to 18
  }

  // Set up the main application window
  stage = new PrimaryStage {
    title.value = "Scala Quiz Game" // Set the window title
    width = 1200  // Set the window width to 1200 pixels
    height = 800 // Set the window height to 800 pixels
    scene = new Scene {
      root = new StackPane {
        padding = Insets(20) // Set padding around the edges of the layout
        children = new VBox {
          spacing = 30 // Set spacing between elements to 30
          alignment = Pos.Center // Center the content in the VBox
          children = Seq(
            new Label("Welcome to the Scala Quiz Game!") {
              style = "-fx-font-size: 36px; -fx-font-weight: bold;" // Style the welcome label with larger font size and bold weight
              alignment = Pos.Center // Center the welcome label
            },
            new Button("Start Quiz") {
              onAction = _ => startQuiz() // Set the action for the button to start the quiz
              prefWidth = 200 // Set preferred width of the button
              prefHeight = 50 // Set preferred height of the button
              style = "-fx-font-size: 18px;" // Style the button with a larger font size
            },
            questionLabel,
            choicesBox,
            scoreLabel
          )
        }
      }
    }
  }

  // Method to start the quiz
  def startQuiz(): Unit = {
    questions = Question.loadQuestions("src/main/resources/questions.json") // Load questions from a JSON file
    currentQuestionIndex = 0 // Reset the current question index
    score = 0 // Reset the score
    scoreLabel.text = s"Score: $score/${questions.length}" // Update the score label
    showNextQuestion() // Display the first question
  }

  // Method to display the next question
  def showNextQuestion(): Unit = {
    if (currentQuestionIndex < questions.length) {
      val question = questions(currentQuestionIndex) // Get the current question
      questionLabel.text = question.question // Display the question text
      choicesBox.children.clear() // Clear previous choices
      question.choices.foreach { choice =>
        val button = new Button(choice) {
          prefWidth = 1200 // Set preferred width of the choice button
          prefHeight = 50 // Set preferred height of the choice button
          onAction = _ => {
            val correct = checkAnswer(choice) // Check if the chosen answer is correct
            if (correct) {
              style = "-fx-background-color: green; -fx-text-fill: white;" // Style the button green if correct
            } else {
              style = "-fx-background-color: red; -fx-text-fill: white;" // Style the button red if incorrect
            }
            // Disable all choice buttons after an answer is selected
            choicesBox.children.foreach {
              case b: Button => b.disable = true
              case _ => // Ignore other types of nodes
            }
            // Delay before showing the next question
            val pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1))
            pause.setOnFinished(_ => showNextQuestion())
            pause.play()
          }
        }
        choicesBox.children.add(button) // Add the button to the choices box
      }
      currentQuestionIndex += 1 // Move to the next question index
    } else {
      endQuiz() // End the quiz if there are no more questions
    }
  }

  // Method to check if the selected answer is correct
  def checkAnswer(selectedAnswer: String): Boolean = {
    val question = questions(currentQuestionIndex - 1) // Get the current question
    val correct = selectedAnswer == question.correctAnswer // Check if the answer is correct
    if (correct) {
      score += 1 // Increase the score if the answer is correct
    }
    scoreLabel.text = s"Score: $score/${questions.length}" // Update the score label
    correct
  }

  // Method to end the quiz and display the final score
  def endQuiz(): Unit = {
    questionLabel.text = s"Quiz Over! Your score is: $score/${questions.length}" // Display the final score
    choicesBox.children.clear() // Clear the choices box
  }
}
