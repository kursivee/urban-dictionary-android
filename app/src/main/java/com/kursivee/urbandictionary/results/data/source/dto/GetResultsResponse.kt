package com.kursivee.urbandictionary.results.data.source.dto

import com.squareup.moshi.Json

data class GetResultsResponse(
    val list: List<Result>
)

data class Result(
    val author: String,
    val currentVote: String,
    val defid: Int,
    val definition: String,
    val example: String,
    val permalink: String,
    @field:Json(name = "sound_urls")
    val soundUrls: List<String>,
    @field:Json(name = "thumbs_up")
    val thumbsUp: Int,
    @field:Json(name = "thumbs_down")
    val thumbsDown: Int,
    val word: String,
    @field:Json(name = "written_on")
    val writtenOn: String
)
