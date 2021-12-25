package ru.mrroot.my_test_8.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.mrroot.my_test_8.view.search.MainActivity.Companion.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory
import ru.mrroot.my_test_8.model.SearchResponse
import ru.mrroot.my_test_8.presenter.RepositoryContract
import ru.mrroot.my_test_8.presenter.SchedulerProvider
import ru.mrroot.my_test_8.presenter.search.SearchSchedulerProvider
import ru.mrroot.my_test_8.repository.GitHubApi
import ru.mrroot.my_test_8.repository.GitHubRepository
import ru.mrroot.my_test_8.view.ScreenState

class SearchViewModel(
    private val repository: RepositoryContract = GitHubRepository(
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubApi::class.java)
    ),
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : ViewModel() {

    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })


    fun subscribeToLiveData() = liveData

//    fun searchGitHub(searchQuery: String) {
//        val compositeDisposable = CompositeDisposable()
//        compositeDisposable.add(
//            repository.searchGithub(searchQuery)
//                .subscribeOn(appSchedulerProvider.io())
//                .observeOn(appSchedulerProvider.ui())
//                .doOnSubscribe { _liveData.value = ScreenState.Loading }
//                .subscribeWith(object : DisposableObserver<SearchResponse>() {
//
//                    override fun onNext(searchResponse: SearchResponse) {
//                        val searchResults = searchResponse.searchResults
//                        val totalCount = searchResponse.totalCount
//                        if (searchResults != null && totalCount != null) {
//                            _liveData.value = ScreenState.Working(searchResponse)
//                        } else {
//                            _liveData.value =
//                                ScreenState.Error(Throwable("Search results or total count are null"))
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        _liveData.value =
//                            ScreenState.Error(
//                                Throwable(
//                                    e.message ?: "Response is null or unsuccessful"
//                                )
//                            )
//                    }
//
//                    override fun onComplete() {}
//                }
//                )
//        )
//    }

    fun searchGitHub(searchQuery: String) {
        _liveData.value = ScreenState.Loading
        viewModelCoroutineScope.launch {
            val searchResponse = repository.searchGithubAsync(searchQuery)
            val searchResults = searchResponse.searchResults
            val totalCount = searchResponse.totalCount
            if (searchResults != null && totalCount != null) {
                _liveData.value = ScreenState.Working(searchResponse)
            } else {
                _liveData.value =
                    ScreenState.Error(Throwable("Search results or total count are null"))
            }
        }
    }

    private fun handleError(error: Throwable) {
        _liveData.value =
            ScreenState.Error(
                Throwable(
                    error.message ?: "Response is null or unsuccessful"
                )
            )

    }

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

}