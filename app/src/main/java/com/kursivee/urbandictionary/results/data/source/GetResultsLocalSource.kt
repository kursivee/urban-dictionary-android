package com.kursivee.urbandictionary.results.data.source

import com.kursivee.urbandictionary.results.data.source.dto.CachedResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class GetResultsLocalSource @Inject constructor(
    private val cachedResultDao: CachedResultDao
) {
    suspend fun getResults(term: String): List<CachedResult> {
        return withContext(Dispatchers.IO) {
            cachedResultDao.findByTerm(term)
        }
    }

    suspend fun insertResults(cachedResults: List<CachedResult>) {
        withContext(Dispatchers.IO) {
            cachedResultDao.insertAll(cachedResults)
        }
    }
}
