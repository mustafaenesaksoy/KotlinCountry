package com.enesaksoy.kotlinulkeler.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.enesaksoy.kotlinulkeler.model.Country
import com.enesaksoy.kotlinulkeler.service.CountryApiService
import com.enesaksoy.kotlinulkeler.service.CountryDatabase
import com.enesaksoy.kotlinulkeler.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService =CountryApiService()
    private var disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    fun refreshdata(){
        val updateTime = customPreferences.gettime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else {
            getDataFromApi()
        }
    }

    fun apidencek(){
        getDataFromApi()
    }

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getallcountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"SQL",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromApi(){
        countryLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        sqliteKaydet(t)
                        Toast.makeText(getApplication(),"API",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showCountries(countrylist : List<Country>){
        countries.value = countrylist
        countryError.value = false
        countryLoading.value = false
    }

    private fun sqliteKaydet(list : List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteallcountries()
            val listLong = dao.insertall(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = listLong.get(i).toInt()
                i++
            }
        }
        showCountries(list)
        customPreferences.savetime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}