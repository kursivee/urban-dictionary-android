package com.kursivee.urbandictionary.results.domain.entity

data class ResultEntity(
    val id: Int,
    val author: String,
    val word: String,
    val definition: String,
    val example: String,
    val thumbsDownCount: Int,
    val thumbsUpCount: Int,
    val writtenOn: String
)
