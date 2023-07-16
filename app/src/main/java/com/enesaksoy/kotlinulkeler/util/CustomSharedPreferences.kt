package com.enesaksoy.kotlinulkeler.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    companion object{
        private val PREFRENCES_TIME = "preferences_time"
        private var sharedPreferences : SharedPreferences? = null
        @Volatile private var instance : CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context : Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPreeferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreeferences(context: Context) : CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun savetime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFRENCES_TIME,time)
        }
    }

    fun gettime() = sharedPreferences?.getLong(PREFRENCES_TIME,0)
}