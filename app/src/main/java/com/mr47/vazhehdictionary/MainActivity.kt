package com.mr47.vazhehdictionary

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mr47.vazhehdictionary.databinding.ActivityMainBinding
import android.content.Intent
import android.net.Uri
import android.content.SharedPreferences
import android.preference.PreferenceManager








class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_vocab, R.id.navigation_about
            )
        )

        navView.setupWithNavController(navController)
        forceRTLIfSupported()
    }
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        val yesBtn = dialog.findViewById(R.id.btn_yes) as Button
        val noBtn = dialog.findViewById(R.id.btn_no) as Button
        yesBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.data = Uri.parse("bazaar://details?id=com.mr47.vazhehdictionary")
            intent.setPackage("com.farsitel.bazaar")
            startActivity(intent)
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = preferences.edit()
            editor.putBoolean("Rated", true).apply();
            editor.apply()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val rated =  preferences.getBoolean("Rated", false)
        if(rated.not()) {
            showDialog()
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "برای خروج مجددا بازگشت را بفشارید...", Toast.LENGTH_SHORT).show()
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }
}