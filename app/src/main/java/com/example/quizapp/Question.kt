package com.example.quizapp

data class Question(
    val questionText: String,
    val imageResId: Int,
    val options: List<AnswerOption>,
    val correctAnswerIndex: Int
)

data class AnswerOption(val text: String)


