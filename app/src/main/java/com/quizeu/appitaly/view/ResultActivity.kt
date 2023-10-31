package com.quizeu.appitaly.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.quizeu.appitaly.R

class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Получите количество правильных ответов из Intent
        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)

        // Далее, используйте значение score для отображения результата
        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "Your score is $score out of 10"

        val restartButton = findViewById<Button>(R.id.restartButton)


        val scoreText = "Your score is $score out of $totalQuestions"
        scoreTextView.text = scoreText

        restartButton.setOnClickListener {
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        // Здесь ничего не делаем, чтобы заблокировать кнопку "Назад"
    }
}
