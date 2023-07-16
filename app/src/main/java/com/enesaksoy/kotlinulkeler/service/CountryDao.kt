package com.enesaksoy.kotlinulkeler.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enesaksoy.kotlinulkeler.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertall(vararg countries : Country) : List<Long>

    @Query("SELECT * FROM country")
    suspend fun getallcountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteallcountries()
}