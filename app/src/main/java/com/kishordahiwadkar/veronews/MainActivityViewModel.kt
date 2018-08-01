package com.kishordahiwadkar.veronews

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivityViewModel : ViewModel() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var apiKey: MutableLiveData<String> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            readApiKey()
        } else {
            signInAnonymously()
        }
    }

    private fun readApiKey() {
        val database = FirebaseDatabase.getInstance()
        val apiKeyRef = database.getReference("api_key")

        apiKeyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("MainActivity", "Value is: " + value!!)
                apiKey.value = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException())
                errorMessage.value = error.message
            }
        })
    }

    private fun signInAnonymously() {
        firebaseAuth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                readApiKey()
            } else {
                // If sign in fails, display a message to the user.
                errorMessage.value = "Authentication Failed"
            }
        }
    }
}