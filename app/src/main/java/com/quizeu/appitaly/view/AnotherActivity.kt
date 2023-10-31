package com.quizeu.appitaly.view

import com.quizeu.appitaly.model.Question
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quizeu.appitaly.adapter.OptionsAdapter
import com.quizeu.appitaly.R
import java.io.IOException

class AnotherActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: List<Question>

    private lateinit var questionImageView: ImageView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var questionTextView: TextView

    private lateinit var adapter: OptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)
        questionTextView = findViewById(R.id.questionTextView)
        questionImageView = findViewById(R.id.questionImageView)
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup)
        submitButton = findViewById(R.id.submitButton)

        try {
            val jsonString = applicationContext.assets.open("quiz.json").bufferedReader().use {
                it.readText()
            }

            val gson = Gson()
            val listType = object : TypeToken<List<Question>>() {}.type
            questions = gson.fromJson(jsonString, listType)

            if (questions.isNotEmpty()) {
                Log.d("TAG", "Первый вопрос1: ${questions.getOrNull(0)}")
                adapter = OptionsAdapter(this, questions[currentQuestionIndex].options)
                Log.d("TAG", "Первый вопрос2: ${questions.getOrNull(0)}")
                updateRadioGroup(questions[currentQuestionIndex].options)
                Log.d("TAG", "Первый вопрос3: ${questions.getOrNull(0)}")
                updateQuestionAndOptions()

                submitButton.setOnClickListener {
                    val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
                    Log.d("TAG", "selectedOptionId: $selectedOptionId")

                    if (selectedOptionId != -1) {
                        checkAnswer()
                    } else {
                        Toast.makeText(this, "Выберите вариант ответа", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Log.e("TAG", "Нет данных вопросов")
                Toast.makeText(this, "Нет данных для вопросов", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: IOException) {
            Log.e("TAG", "Ошибка при загрузке данных: $ex")
            Toast.makeText(this, "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show()
        }

        Log.d("TAG", "Первый вопрос7: ${questions.getOrNull(0)}")
    }


    private fun updateRadioGroup(options: List<String>) {
        optionsRadioGroup.removeAllViews()

        for (option in options) {
            val radioButton = RadioButton(this)
            radioButton.text = option
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.white)) // Установите цвет текста

            // Установка цвета для фона радиокнопок
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)) // Укажите цвет фона
            radioButton.buttonTintList = colorStateList

            optionsRadioGroup.addView(radioButton)
        }
    }


    private fun updateQuestionAndOptions() {
        val currentQuestion = questions[currentQuestionIndex]
        questionTextView.text = currentQuestion.question
        val questionImageView = findViewById<ImageView>(R.id.questionImageView)

        // Загрузка изображения из URL с помощью Glide
        Glide.with(this)
            .load(currentQuestion.image_url)
            .into(questionImageView)

        // Проверяем, что количество RadioButton равно количеству вариантов ответов
        if (optionsRadioGroup.childCount == currentQuestion.options.size) {
            for (i in 0 until optionsRadioGroup.childCount) {
                val radioButton = optionsRadioGroup.getChildAt(i) as RadioButton
                radioButton.text = currentQuestion.options[i]
            }
        } else {
            // Создайте RadioButton или очистите RadioGroup и добавьте новые RadioButton, если их количество не совпадает
            optionsRadioGroup.removeAllViews()
            for (option in currentQuestion.options) {
                val radioButton = RadioButton(this)
                radioButton.layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                radioButton.text = option
                optionsRadioGroup.addView(radioButton)
            }
        }
        optionsRadioGroup.clearCheck()

    }

    private fun checkAnswer() {
        val selectedOptionId = optionsRadioGroup.checkedRadioButtonId


        if (selectedOptionId != -1) {
            val selectedOptionIndex = optionsRadioGroup.indexOfChild(findViewById(selectedOptionId))
            val currentQuestion = questions[currentQuestionIndex]
            val correctAnswerIndex = currentQuestion.options.indexOf(currentQuestion.answer)

            if (selectedOptionIndex == correctAnswerIndex) {
                score++
            }

            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestionAndOptions()
            } else {
                showResultDialog()
            }
        } else {
            Toast.makeText(this, "Выберите вариант ответа", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showResultDialog() {
        val resultIntent = Intent(this, ResultActivity::class.java)
        resultIntent.putExtra("score", score)
        resultIntent.putExtra("totalQuestions", questions.size)
        startActivity(resultIntent)
    }

    override fun onBackPressed() {
        // Блокировка кнопки "Назад"
    }
}