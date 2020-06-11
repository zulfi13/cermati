package com.test.cermati.features.home.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.test.cermati.core.model.Search
import com.test.cermati.core.ui.BaseViewModel
import com.test.cermati.core.network.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : BaseViewModel() {
    private val services = SearchServices.instance()

    val searchTextField = ObservableField<String>("")
    private val search: String get() {return searchTextField.get() ?: ""}

    val searchData = MutableLiveData<MutableList<Search>>()

    val isLoading = ObservableBoolean(false)
    val isPromosEmpty = ObservableBoolean(false)

    val texta = ObservableField<String>("")

    fun searchByName(page: Int, offset: Int){
        services.getUserByName(search ,page, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.set(true) }
            .doAfterTerminate { isLoading.set(false) }
            .subscribe(object: ResponseSingle<Search>(compositeDisposable){
                override fun onResponseSuccess(data: List<Search>?) {
                    data?.let { resp ->
                        val items = resp
                        searchData.value = items.toMutableList()
                        isPromosEmpty.set(items.isNullOrEmpty())
                        texta.set("User with that criteria not found!")
                    } ?: run {
                        isPromosEmpty.set(true)
                        texta.set("User with that criteria not found!")
                    }
                }

                override fun onResponseError(msg: String, code: Int) {
                    errorResponse.postValue(ErrorResponse(code, msg))
                }
            })
    }
}