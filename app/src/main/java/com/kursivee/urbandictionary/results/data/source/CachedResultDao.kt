package com.kursivee.urbandictionary.results.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kursivee.urbandictionary.results.data.source.dto.CachedResult

@Dao
interface CachedResultDao {
    @Query("SELECT * FROM result WHERE search_term = :term")
    fun findByTerm(term: String): List<CachedResult>

    @Insert
    fun insertAll(results: List<CachedResult>)
}
