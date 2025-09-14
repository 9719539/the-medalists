package com.example.themedalists.models

data class Medalist(
    val country: String,
    val iocCode: String,
    val timesCompeted: Int,
    val goldMedals: Int,
    val silverMedals: Int,
    val bronzeMedals: Int
)

