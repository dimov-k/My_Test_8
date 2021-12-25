package ru.mrroot.my_test_8.view.details

import ru.mrroot.my_test_8.view.ViewContract

internal interface ViewDetailsContract : ViewContract {
    fun setCount(count: Int)
}