package com.example.quizapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    // Инициализируйте список вопросов
    private val questions = listOf(
        Question(
            "Who was the first British player to win league titles in four countries?",
            R.drawable.image1,
            listOf(
                AnswerOption("Wayne Rooney"),
                AnswerOption("David Beckham"),
                AnswerOption("Harry Kane")
            ),
            1
        ),
        Question(
            "Which is the only country that has taken part in every FIFA World Cup?",
            R.drawable.image2,
            listOf(
                AnswerOption("France"),
                AnswerOption("Brazil"),
                AnswerOption("Argentina")
            ),
            1
       )
        ,
        Question(
            "How many disciplines are there in men's gymnastics?",
            R.drawable.image3,
            listOf(
                AnswerOption("Four"),
                AnswerOption("Five"),
                AnswerOption("Six")
            ),
            2
        )
        //,
//        Question(
//            "What is the record for the most red cards in one football match?",
//            R.drawable.image4,
//            listOf(
//                AnswerOption("36"),
//                AnswerOption("37"),
//                AnswerOption("38")
//            ),
//            0
//        ),
//        Question(
//            "Which African country was the first to qualify for the World Cup?",
//            R.drawable.image5,
//            listOf(
//                AnswerOption("Morocco"),
//                AnswerOption("Nigeria"),
//                AnswerOption("Egypt")
//            ),
//            2
//        ),
//        Question(
//            "Which city was the first to host the Olympics twice?",
//            R.drawable.image6,
//            listOf(
//                AnswerOption("Brasilia"),
//                AnswerOption("Paris"),
//                AnswerOption("Vancouver")
//            ),
//            1
//        ),
//        Question(
//            "What does NBA mean?",
//            R.drawable.image7,
//            listOf(
//                AnswerOption("National Running Association"),
//                AnswerOption("National Baseball Association"),
//                AnswerOption("National Basketball Association")
//            ),
//            2
//        ),
//        Question(
//            "Which part of the body in football cannot touch the ball?",
//            R.drawable.image8,
//            listOf(
//                AnswerOption("Knees"),
//                AnswerOption("Elbows"),
//                AnswerOption("Hands")
//            ),
//            2
//        ),
//        Question(
//            "In what year were women allowed to participate in the modern Olympic Games?",
//            R.drawable.image9,
//            listOf(
//                AnswerOption("1900"),
//                AnswerOption("1954"),
//                AnswerOption("1889")
//            ),
//            0
//        ),
//        Question(
//            "What is the only city in the United States that has won three of the four " +
//                    "major professional sports championships in the same year?",
//            R.drawable.image10,
//            listOf(
//                AnswerOption("Kentucky"),
//                AnswerOption("Iowa"),
//                AnswerOption("Detroit")
//            ),
//            2
//        ),
//        Question(
//            "Who is the youngest heavyweight boxing champion of the world?",
//                    R.drawable.image11,
//            listOf(
//                AnswerOption("Evander Holyfield"),
//                AnswerOption("Mike Tyson"),
//                AnswerOption("George Foreman")
//            ),
//            1
//        ),
//        Question(
//            "How many medals did China win at the Beijing Olympics?",
//                    R.drawable.image12,
//            listOf(
//                AnswerOption("100"),
//                AnswerOption("89"),
//                AnswerOption("96")
//            ),
//            0
//        ),
//        Question(
//            "How old was the youngest professional football player?",
//                    R.drawable.image13,
//            listOf(
//                AnswerOption("12"),
//                AnswerOption("18"),
//                AnswerOption("16")
//            ),
//            0
//        ),
//        Question(
//            "How many players are on a baseball team?",
//                    R.drawable.image14,
//            listOf(
//                AnswerOption("10"),
//                AnswerOption("9"),
//                AnswerOption("8")
//            ),
//            1
//        ),
//        Question(
//            "What was the fastest goal in the history of the World Cup?",
//                    R.drawable.image15,
//            listOf(
//                AnswerOption("9.8 seconds"),
//                AnswerOption("11.8 seconds"),
//                AnswerOption("10.8 seconds")
//            ),
//            2
//        )

    )
    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var questionImageView: ImageView
    private lateinit var option1RadioButton: RadioButton
    private lateinit var option2RadioButton: RadioButton
    private lateinit var option3RadioButton: RadioButton
    private lateinit var submitButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submitButton = findViewById<Button>(R.id.submitButton)
        val optionsRadioGroup = findViewById<RadioGroup>(R.id.optionsRadioGroup)

        questionImageView = findViewById(R.id.questionImageView)
        option1RadioButton = findViewById(R.id.option1RadioButton)
        option2RadioButton = findViewById(R.id.option2RadioButton)
        option3RadioButton = findViewById(R.id.option3RadioButton)

        // Обновление экрана с вопросом и вариантами ответов
        updateQuestionAndOptions()

        submitButton.setOnClickListener {
            // Проверьте, был ли выбран хотя бы один вариант ответа
            val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
            if (selectedOptionId != -1) {
                // Пользователь выбрал вариант ответа, можно перейти к следующему вопросу
                checkAnswer()
            } else {
                // Если ничего не выбрано, выведите сообщение пользователю, что нужно выбрать ответ.
                Toast.makeText(this, "Выберите вариант ответа", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateQuestionAndOptions() {
        val currentQuestion = questions[currentQuestionIndex]
        val questionImageView = findViewById<ImageView>(R.id.questionImageView)
        val option1RadioButton = findViewById<RadioButton>(R.id.option1RadioButton)
        val option2RadioButton = findViewById<RadioButton>(R.id.option2RadioButton)
        val option3RadioButton = findViewById<RadioButton>(R.id.option3RadioButton)
        val questionTextView = findViewById<TextView>(R.id.questionTextView) // Добавлен TextView для текста вопроса

        // Установите текст в TextView на основе текущего вопроса
        questionTextView.text = currentQuestion.questionText

        // Сбросить выбор в RadioGroup
        val optionsRadioGroup = findViewById<RadioGroup>(R.id.optionsRadioGroup)
        optionsRadioGroup.clearCheck()

        questionImageView.setImageResource(currentQuestion.imageResId)
        option1RadioButton.text = currentQuestion.options[0].text
        option2RadioButton.text = currentQuestion.options[1].text
        option3RadioButton.text = currentQuestion.options[2].text
    }

    private fun checkAnswer() {
        val selectedOptionId = findViewById<RadioGroup>(R.id.optionsRadioGroup).checkedRadioButtonId
        val selectedOptionIndex = when (selectedOptionId) {
            R.id.option1RadioButton -> 0
            R.id.option2RadioButton -> 1
            R.id.option3RadioButton -> 2
            else -> -1
        }

        if (selectedOptionIndex == questions[currentQuestionIndex].correctAnswerIndex) {
            score++
        }

        // Перейти к следующему вопросу или показать результат
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            updateQuestionAndOptions()
        } else {
            showResultDialog()
        }
    }

    private fun showResultDialog() {
        val resultIntent = Intent(this, ResultActivity::class.java)

        // Добавьте количество правильных ответов в Intent
        resultIntent.putExtra("score", score)

        // Добавьте общее количество вопросов в Intent
        resultIntent.putExtra("totalQuestions", questions.size)

        // Перейдите к ResultActivity
        startActivity(resultIntent)
    }


    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        updateQuestionAndOptions()
    }
}
