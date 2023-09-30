package com.example.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    // Инициализируйте список вопросов
    private val questions = listOf(
        Question(
            "Какое столицей Франции?",
            R.drawable.image1,
            listOf(
                AnswerOption("Лондон"),
                AnswerOption("Париж"),
                AnswerOption("Берлин")
            ),
            1
        ),
        Question(
            "Какой самый высокий город в мире?",
            R.drawable.image1,
            listOf(
                AnswerOption("Москва"),
                AnswerOption("Нью-Йорк"),
                AnswerOption("Ла-Пас")
            ),
            2
        ),
        Question(
            "Какой химический символ для водорода?",
            R.drawable.image1,
            listOf(
                AnswerOption("Hg"),
                AnswerOption("He"),
                AnswerOption("H")
            ),
            2
        ),
        Question(
            "Какое самое большое озеро в мире?",
            R.drawable.image1,
            listOf(
                AnswerOption("Каспийское море"),
                AnswerOption("Байкал"),
                AnswerOption("Онтарио")
            ),
            0
        ),
        Question(
            "Какая планета в Солнечной системе самая большая?",
            R.drawable.image1,
            listOf(
                AnswerOption("Меркурий"),
                AnswerOption("Венера"),
                AnswerOption("Юпитер")
            ),
            2
        ),
        Question(
            "Сколько континентов на Земле?",
            R.drawable.image1,
            listOf(
                AnswerOption("3"),
                AnswerOption("6"),
                AnswerOption("7")
            ),
            2
        ),
        Question(
            "Какая столица Японии?",
            R.drawable.image1,
            listOf(
                AnswerOption("Сеул"),
                AnswerOption("Пекин"),
                AnswerOption("Токио")
            ),
            2
        ),
        Question(
            "Какая самая длинная река в мире?",
            R.drawable.image1,
            listOf(
                AnswerOption("Миссисипи"),
                AnswerOption("Амазонка"),
                AnswerOption("Нил")
            ),
            2
        ),
        Question(
            "Какое химическое вещество имеет символ 'Au'?",
            R.drawable.image1,
            listOf(
                AnswerOption("Аргон"),
                AnswerOption("Серебро"),
                AnswerOption("Золото")
            ),
            2
        ),
        Question(
            "Какая страна известна как 'Страна восходящего солнца'?",
            R.drawable.image1,
            listOf(
                AnswerOption("Индия"),
                AnswerOption("Китай"),
                AnswerOption("Япония")
            ),
            2
        ),
        Question(
            "Сколько месяцев в году имеют 28 дней?",
            R.drawable.image1,
            listOf(
                AnswerOption("1"),
                AnswerOption("6"),
                AnswerOption("12")
            ),
            2
        ),
        Question(
            "Какая самая высокая гора в мире?",
            R.drawable.image1,
            listOf(
                AnswerOption("Альпы"),
                AnswerOption("Килиманджаро"),
                AnswerOption("Эверест")
            ),
            2
        ),
        Question(
            "Какое животное является символом Австралии?",
            R.drawable.image1,
            listOf(
                AnswerOption("Кенгуру"),
                AnswerOption("Коала"),
                AnswerOption("Пингвин")
            ),
            0
        ),
        Question(
            "Какая самая длинная река в России?",
            R.drawable.image1,
            listOf(
                AnswerOption("Обь"),
                AnswerOption("Волга"),
                AnswerOption("Лена")
            ),
            1
        )
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

        questionImageView = findViewById(R.id.questionImageView)
        option1RadioButton = findViewById(R.id.option1RadioButton)
        option2RadioButton = findViewById(R.id.option2RadioButton)
        option3RadioButton = findViewById(R.id.option3RadioButton)
        submitButton = findViewById(R.id.submitButton)

        // Обновление экрана с вопросом и вариантами ответов
        updateQuestionAndOptions()

        submitButton.setOnClickListener {
            // Обработка ответа
            checkAnswer()
        }
    }

    private fun updateQuestionAndOptions() {
        val currentQuestion = questions[currentQuestionIndex]

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
            showResult()
        }
    }

    private fun showResult() {
        val resultText = "Ваш результат: $score из ${questions.size}"
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Результат")
            .setMessage(resultText)
            .setPositiveButton("Рестарт") { _, _ ->
                restartQuiz()
            }
            .setCancelable(false)
            .create()
        alertDialog.show()
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        updateQuestionAndOptions()
    }
}
