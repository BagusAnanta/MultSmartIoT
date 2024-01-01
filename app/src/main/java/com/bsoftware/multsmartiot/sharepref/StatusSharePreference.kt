package com.bsoftware.multsmartiot.sharepref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class StatusSharePreference(val activity : Activity) {
    private val statusSharePref : SharedPreferences = activity.getSharedPreferences(STATUS_NAME, Context.MODE_PRIVATE)
    private val statusEdit = statusSharePref.edit()


    fun setStatus(status : Boolean){
        statusEdit.apply {
            putBoolean(STATUS_KEY,status)
            commit()
        }
    }

    fun getStatus() : Boolean{
        return statusSharePref.getBoolean(STATUS_KEY,false)
    }


    companion object{
        private val STATUS_NAME : String = "SharePrefStatus"
        private val STATUS_KEY : String = "StatusKey"
    }
}