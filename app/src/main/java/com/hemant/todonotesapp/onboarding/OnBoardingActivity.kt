package com.hemant.todonotesapp.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.hemant.todonotesapp.R
import com.hemant.todonotesapp.utils.PrefConstant
import com.hemant.todonotesapp.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick,OnBoardingTwoFragment.OnOptionClick{
    lateinit var viewPager:ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        sharedPreferences=getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun bindViews() {

        viewPager=findViewById(R.id.ViewPager)
        val adapter=FragmentAdapter(supportFragmentManager)
        viewPager.adapter=adapter
    }

    override fun onClick() {
        viewPager.currentItem=1
    }

    override fun onOptionDone() {
        //For the 2nd Fragment
        editor=sharedPreferences.edit()
       editor.putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,true)
        editor.apply()
        val intent= Intent(this@OnBoardingActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionBack() {
        viewPager.currentItem=0
    }
}