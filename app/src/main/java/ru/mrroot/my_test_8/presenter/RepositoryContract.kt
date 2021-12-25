package ru.mrroot.my_test_8.presenter

import io.reactivex.Observable
import ru.mrroot.my_test_8.model.SearchResponse
import ru.mrroot.my_test_8.repository.RepositoryCallback

interface RepositoryContract {

    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String
    ): Observable<SearchResponse>

    suspend fun searchGithubAsync(
        query: String
    ): SearchResponse

}