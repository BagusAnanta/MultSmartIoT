package com.bsoftware.multsmartiot.firebase

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bsoftware.multsmartiot.dataclass.HumTempDataClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class FirebaseRealtimeDatabase {
   fun initDatabase() : DatabaseReference{
       val databaseReference : DatabaseReference = Firebase.database("https://multsmartiot-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
       return databaseReference
   }

    @Composable
    fun getHumTempDataList(databasePref : DatabaseReference) : SnapshotStateList<HumTempDataClass>{
        val dataList = remember { mutableStateListOf<HumTempDataClass>() }

        LaunchedEffect(databasePref){
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                   val dataHumTemp = snapshot.getValue<HumTempDataClass>()
                    Log.d("DataHumidity",dataHumTemp?.humidity.toString())
                    Log.d("DataTemperature", dataHumTemp?.temperature.toString())
                    Log.d("DataStatus", dataHumTemp?.status.toString())
                    Log.d("DataOutput", dataHumTemp?.output.toString())
                    Log.d("DataLampStat",dataHumTemp?.lampstatus.toString())
                    dataList.add(dataHumTemp!!)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("getHumTemp() Error At :", error.details)
                }

            }
            databasePref.addValueEventListener(postListener)
        }
        return dataList
    }

    fun SetlampStatus(databasePref: DatabaseReference,isOn : Boolean = false){
        databasePref.child("lampstatus").setValue(isOn)
    }
}