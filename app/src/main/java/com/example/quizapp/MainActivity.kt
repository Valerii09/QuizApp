package com.example.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {
    // Инициализируйте список вопросов
    private val questions = listOf(
        Question("Вопрос 1", R.drawable.image1, listOf(
            AnswerOption("Ответ 1"),
            AnswerOption("Ответ 2"),
            AnswerOption("Ответ 3")
        ), 0),
        // Добавьте остальные вопросы
        // ...
    )

    private var currentQuestionIndex = 0
    private var score = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionImageView = findViewById<ImageView>(R.id.questionImageView)
        val option1RadioButton = findViewById<RadioButton>(R.id.option1RadioButton)
        val option2RadioButton = findViewById<RadioButton>(R.id.option2RadioButton)
        val option3RadioButton = findViewById<RadioButton>(R.id.option3RadioButton)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Обновление экрана с вопросом и вариантами ответов
        updateQuestionAndOptions()

        submitButton.setOnClickListener {
            // Обработка ответа
            checkAnswer()
        }
    }

    private fun updateQuestionAndOptions() {
        val currentQuestion = questions[currentQuestionIndex]
        val questionImageView = findViewById<ImageView>(R.id.questionImageView)
        val option1RadioButton = findViewById<RadioButton>(R.id.option1RadioButton)
        val option2RadioButton = findViewById<RadioButton>(R.id.option2RadioButton)
        val option3RadioButton = findViewById<RadioButton>(R.id.option3RadioButton)

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
        // Отобразите результат и кнопку рестарта
        // Можно создать новую активность или использовать AlertDialog
        // с кнопкой "Рестарт".
    }
}
