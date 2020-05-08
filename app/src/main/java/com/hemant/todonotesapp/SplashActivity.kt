package com.hemant.todonotesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.hemant.todonotesapp.MyNotesActivity
import com.hemant.todonotesapp.PrefConstant
import com.hemant.todonotesapp.R


class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreference()
        checkLoginStatus()

    }



    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREERENCE_NAME, Context.MODE_PRIVATE)
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
