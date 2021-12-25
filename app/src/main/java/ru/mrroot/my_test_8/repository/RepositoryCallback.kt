package ru.mrroot.my_test_8.repository

import retrofit2.Response
import ru.mrroot.my_test_8.model.SearchResponse

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}