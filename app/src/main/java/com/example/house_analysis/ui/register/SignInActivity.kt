package com.example.house_analysis.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivitySignInBinding
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.domain.usecase.auth.LoginUseCase
import com.example.house_analysis.ui.password.RestorePass
import com.example.house_analysis.ui.profile.MainAccountActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setListenerForViews()
        errorOccurredInEmail(binding.editTextMail)

    }

    private fun setListenerForViews() {
        val listener = View.OnClickListener {
            when (it.id) {
                R.id.backButton -> finish()
                R.id.buttonSignIn -> loginRequest()
                R.id.forgotPassword -> openActivity(RestorePass::class.java)
                R.id.signUpTextView -> openActivity(SignUpActivity::class.java)
            }
        }
        binding.backButton.setOnClickListener(listener)
        binding.buttonSignIn.setOnClickListener(listener)
        binding.forgotPassword.setOnClickListener(listener)
        binding.signUpTextView.setOnClickListener(listener)
    }

    private fun loginRequest() { // TODO("Добавить проверку на пустые поля логина и пароля")
        lifecycleScope.launch {
            val success = LoginUseCase().invoke(
                UserLoginModel(
                    binding.editTextMail.editText?.text.toString(),
                    binding.editTextPassword.editText?.text.toString()
                )
            )
            if (success) {
                isRememberPassword()
                openActivity(MainAccountActivity::class.java)
            } else {
                applicationContext.toast(resources.getText(R.string.login_fail))
            }
        }
    }

    private fun isRememberPassword() {
        if (binding.isRememberPassword.isChecked) {
            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit()
                .putString("email", binding.editTextMail.editText?.text.toString())
                .putString("password", binding.editTextPassword.editText?.text.toString())
                .apply()
        }
    }

    private fun Context.toast(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }

    private fun errorOccurredInEmail(editTextMail: TextInputLayout) {
        editTextMail.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
            if (!inputText.isNullOrEmpty()) {
                if ("@" !in inputText) {
                    editTextMail.error = "Неверный формат почты"
                } else {
                    editTextMail.isErrorEnabled = false
                }
            } else {
                editTextMail.isErrorEnabled = false
            }
        }
    }
}