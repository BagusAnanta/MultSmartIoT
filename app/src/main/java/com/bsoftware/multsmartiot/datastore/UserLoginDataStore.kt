package com.bsoftware.multsmartiot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bsoftware.multsmartiot.dataclass.UserLoginDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREF_LOGIN_NAME = "LoginDataPreference"
class UserLoginDataStore(private val context : Context) {

    companion object {
        private val Context.userLoginStore  : DataStore<Preferences> by preferencesDataStore(name = PREF_LOGIN_NAME)
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PASSWORD = stringPreferencesKey("user_password")
        private val USER_STATUS = booleanPreferencesKey("user_status")
    }

    suspend fun storeUserData(loginDataClass : UserLoginDataClass){
        context.userLoginStore.edit { preference ->
            preference[USER_EMAIL] = loginDataClass.email.toString()
            preference[USER_PASSWORD] = loginDataClass.password.toString()
        }
    }

    suspend fun storeStatus(status : Boolean){
        context.userLoginStore.edit { preference ->
            preference[USER_STATUS] = status
        }
    }

    val getStatus : Flow<Boolean> = context.userLoginStore.data.map { preference ->
        preference[USER_STATUS] ?: false
    }

   val getUserDataLoginFlow : Flow<UserLoginDataClass> = context.userLoginStore.data.map { preference ->
       val email = preference[USER_EMAIL] ?: ""
       val password = preference[USER_PASSWORD] ?: ""
       UserLoginDataClass(email, password)
   }



}