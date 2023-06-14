package com.example.house_analysis.ui.profile

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityLanguageBinding
import java.util.Locale

class LanguageActivity : AppCompatActivity() {
    lateinit var binding: ActivityLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()
    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun changeLanguage(){
        binding.russianLanguageLinear.setOnClickListener {
            val newLocale = Locale("ru")
            Locale.setDefault(newLocale)
            val configuration = Configuration()
            configuration.setLocale(newLocale)
            baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
            recreate()
        }

    }

}