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
import com.example.house_analysis.network.api.requests.RequestRepository
import com.example.house_analysis.network.model.request.UserLoginData
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
                finish()
            }, splashTimeout)
        }
    }

    private fun loginRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            val success = networkRepository.login(
                UserLoginData(
                    email!!,
                    password!!
                )
            )
            if (success) {
                openActivity(MainAccountActivity::class.java)
                finish()
            } else {
                Log.d("Network", "Autologin didn't work")
            }
        }
    }

    private fun isUserRemembered(): Boolean {
        val sharedPreferences = getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)

        email = sharedPreferences?.getString("email", "")
        password = sharedPreferences?.getString("password", "")

        return !(email == null || password == null)
    }

    private fun openActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }
}