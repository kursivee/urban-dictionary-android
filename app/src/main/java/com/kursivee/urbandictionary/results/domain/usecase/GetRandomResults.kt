package com.kursivee.urbandictionary.results.domain.usecase

import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity
import com.kursivee.urbandictionary.results.domain.repository.ResultsRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetRandomResults @Inject constructor(
    private val resultsRepository: ResultsRepository
) {
    suspend operator fun invoke(): NetworkResponse<List<ResultEntity>> {
        return resultsRepository.getRandomResults()
    }
}
