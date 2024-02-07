package com.bsoftware.multsmartiot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val PREF_LAMP_STATUS = "LampStatus"

class LampStatusDataStore(private val context : Context) {

    companion object{
        private val Context.LampStatusDataStore : DataStore<Preferences> by preferencesDataStore(name = PREF_LAMP_STATUS)
        private val LAMP_STATUS = booleanPreferencesKey("lamp_status")
    }

    suspend fun storeLampStatus(status : Boolean){
        context.LampStatusDataStore.edit {preference ->
            preference[LAMP_STATUS] = status
        }
    }

    val getDataLampStatus : Flow<Boolean> = context.LampStatusDataStore.data.map {preference ->
        preference[LAMP_STATUS] ?: false
    }
}