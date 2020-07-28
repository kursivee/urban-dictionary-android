package com.kursivee.urbandictionary.search.domain.usecase

import arrow.core.Either
import com.kursivee.urbandictionary.common.ext.success
import com.kursivee.urbandictionary.common.network.alias.NetworkResponse
import com.kursivee.urbandictionary.search.domain.entity.AutoCompleteResult
import com.kursivee.urbandictionary.search.domain.repository.AutoCompleteRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAutoCompleteResults @Inject constructor(
    private val autoCompleteRepository: AutoCompleteRepository
) {
    suspend operator fun invoke(input: String): NetworkResponse<List<AutoCompleteResult>> {
        if (input.isEmpty()) return Either.success(emptyList())
        return autoCompleteRepository.getAutoCompleteResults(input)
    }
}
