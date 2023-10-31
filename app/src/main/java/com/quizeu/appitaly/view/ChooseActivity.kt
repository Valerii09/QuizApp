package com.quizeu.appitaly.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.quizeu.appitaly.R

class ChooseActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        val factsButton: Button = findViewById(R.id.facts_button)
        val quizButton: Button = findViewById(R.id.quiz_button)

        factsButton.setOnClickListener {
            val intent = Intent(this, FootballFactsActivity::class.java)
            startActivity(intent)
        }

        quizButton.setOnClickListener {
            val intent = Intent(this, AnotherActivity::class.java)
            startActivity(intent)
        }
    }
}
