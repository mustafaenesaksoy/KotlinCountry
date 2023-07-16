package com.enesaksoy.kotlinulkeler.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesaksoy.kotlinulkeler.model.Country
import com.enesaksoy.kotlinulkeler.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application : Application) : BaseViewModel(application) {
    val countrylivedata = MutableLiveData<Country>()

    fun getdataFromRoom(id : Int){
        launch {
            val country = CountryDatabase(getApplication()).countryDao().getCountry(id)
            countrylivedata.value = country
        }
    }

}