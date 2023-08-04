package com.example.house_analysis.ui.additional

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.house_analysis.MainActivity
import com.example.house_analysis.R
import com.example.house_analysis.data.repository.RequestRepository
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.domain.usecase.auth.LoginUseCase
import com.example.house_analysis.ui.profile.MainAccountActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private val splashTimeout: Long = 1000

    private val networkRepository = RequestRepository
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        if (isUserRemembered()) {
            loginRequest()
        } else {
            Handler().postDelayed({
                openActivity(MainActivity::class.java)
            }, splashTimeout)
        }
    }

    private fun loginRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            val success = LoginUseCase().invoke(
                UserLoginModel(
                    email!!,
                    password!!
                )
            )
            if (success) {
                openActivity(MainAccountActivity::class.java)
                finish()
            } else {
                Log.e("Network", "Autologin didn't work")
                openActivity(MainActivity::class.java)
            }
        }
    }

    private fun isUserRemembered(): Boolean {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        email = sharedPreferences?.getString("email", null)
        password = sharedPreferences?.getString("password", null)

        return !(email == null || password == null)
    }

    private fun openActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
        finish()
    }
}