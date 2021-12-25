package ru.mrroot.my_test_8.presenter.search

import ru.mrroot.my_test_8.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
}