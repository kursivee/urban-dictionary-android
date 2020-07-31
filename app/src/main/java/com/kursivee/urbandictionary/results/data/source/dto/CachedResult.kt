package com.kursivee.urbandictionary.results.data.source.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result")
data class CachedResult(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "definition") val definition: String,
    @ColumnInfo(name = "example") val example: String,
    @ColumnInfo(name = "thumbs_down_count") val thumbsDownCount: Int,
    @ColumnInfo(name = "thumbs_up_count") val thumbsUpCount: Int,
    @ColumnInfo(name = "written_on") val writtenOn: String,
    @ColumnInfo(name = "search_term") val searchTerm: String,
    @PrimaryKey(autoGenerate = true) val primaryKey: Int = 0
)
