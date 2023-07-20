package com.example.house_analysis.ui.profile

import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityAccountMainBinding
import com.example.house_analysis.ui.profile.bottom_nav.AddFragment
import com.example.house_analysis.ui.profile.bottom_nav.TasksFragment
import com.example.house_analysis.ui.profile.bottom_nav.SearchFragment
import com.example.house_analysis.ui.profile.top_nav.ProfileFragment
import com.example.house_analysis.ui.profile.top_nav.SettingsFragment

class MainAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forClickSettingImageView()
        forClickLabelImageView()
        forClickProfileImageView()
//        forDefaultFragment()

        openDefaultFragment()
        bottomNavigationListener()

    }

    fun forClickSettingImageView(){
        binding.settingsImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = SettingsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    fun forClickLabelImageView(){
        binding.labelImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = TasksFragment() //TODO("Заменить на Label фрагмент")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    fun forClickProfileImageView(){
        binding.profileImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = ProfileFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    private fun bottomNavigationListener(){
        binding.navBar.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.home ->
                        openFragment(TasksFragment())
                    R.id.search ->
                        openFragment(SearchFragment())
                    R.id.add ->
                        openFragment(AddFragment())
                    R.id.dots -> bottomSheetDialog()
                    else -> false
                }
            }
        }

//    Navigating each Fragment
    private fun openFragment(fragment: Fragment): Boolean{
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }
    private fun openDefaultFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, TasksFragment())
            .commit()
    }

    private fun bottomSheetDialog(): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        when (currentFragment) {
            is TasksFragment -> currentFragment.openTaskDotsDialog()
//            is SubtasksFragment ->
        }

        return true
    }
}