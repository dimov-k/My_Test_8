package ru.mrroot.my_test_8.presenter.details

import ru.mrroot.my_test_8.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}