package com.hemant.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.hemant.todonotesapp.R
import com.hemant.todonotesapp.utils.PrefConstant


class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreference()
        checkLoginStatus()
        getFcmToken()

    }

    private fun getFcmToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("FiresPlace", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                  //  val msg = getString(R.string.msg_token_fmt, token)
                  //  Log.d(TAG, msg)
                    Toast.makeText(baseContext, "Welcome", Toast.LENGTH_SHORT).show()
                })
    }


    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
    private fun checkLoginStatus() {
    val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        if(isLoggedIn)
        {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java )
            startActivity(intent)
        }
        else
        {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java )
            startActivity(intent)
        }
    }


}
