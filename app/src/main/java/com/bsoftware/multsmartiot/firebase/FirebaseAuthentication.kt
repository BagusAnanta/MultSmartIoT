package com.bsoftware.multsmartiot.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthentication {
    private lateinit var auth : FirebaseAuth

    // init firebase
    fun initFirebaseAuth(){
        auth = Firebase.auth
    }

    fun createDataUser(
        email : String,
        password : String,
        onSuccess : () -> Unit = {},
        onFailed : () -> Unit = {},
        activity : Activity
    ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity){ task ->
                if (task.isSuccessful){
                    onSuccess()
                } else {
                    onFailed()

                    // check a exception
                    val exception = task.exception
                    Log.e("createDataUser() Exception At: ", exception.toString())
                }
            }
    }

    fun signInDataUser(
        email : String,
        password: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
        activity: Activity
    ){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity) { task ->
                if(task.isSuccessful){
                    onSuccess()
                } else {
                    onFailed()
                }
            }
    }

    fun getUsernameFromEmail() : String?{
        return auth.currentUser?.displayName
    }

    fun getEmail() : String?{
        return auth.currentUser?.email
    }
}