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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class FirebaseRealtimeDatabase {
    private lateinit var databaseReference: DatabaseReference
   fun initDatabase() : DatabaseReference{
       databaseReference = Firebase.database("https://multsmartiot-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
       return databaseReference
   }

    @Composable
    fun getHumTempDataList(databasePref : DatabaseReference) : SnapshotStateList<HumTempDataClass>{
        val dataList = remember { mutableStateListOf<HumTempDataClass>() }

        // get a time in here
        var lastUpdateTime by remember { mutableStateOf(System.currentTimeMillis()) }

        LaunchedEffect(databasePref){
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        dataList.clear()

                        for(dataSnapShot in snapshot.children){
                            val dataMap = dataSnapShot.value as? Map<*,*>?

                            if(dataMap != null){
                                val humTempDataClass = HumTempDataClass(
                                    humidity = dataMap["humidity"] as? String ?: "",
                                    temperature = dataMap["temperature"] as? String ?: "",
                                    status = dataMap["status"] as? Boolean ?: false
                                )
                                dataList.add(humTempDataClass)
                                // last update in here
                                lastUpdateTime = System.currentTimeMillis()
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("getHumTemp() Error At :", error.details)
                }

            }
            databaseReference.addValueEventListener(postListener)
        }

        // check a data in 5 minute is update or not
        LaunchedEffect(lastUpdateTime){
            while (true){
                delay(1000)
                val currentTime = System.currentTimeMillis()
                if(currentTime - lastUpdateTime > 5 * 60 * 1000){
                    // in here we make a data shutdown
                    val humTempDataClass = HumTempDataClass(
                        humidity = "0",
                        temperature = "0.00",
                        status = false
                    )
                    dataList.add(humTempDataClass)
                }
            }
        }

        return dataList
    }
}