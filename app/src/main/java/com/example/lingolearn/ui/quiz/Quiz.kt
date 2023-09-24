package com.example.lingolearn.ui.quiz

data class Quiz (
    val text: String ?= null,
    val answers: List<String> ?= null
)