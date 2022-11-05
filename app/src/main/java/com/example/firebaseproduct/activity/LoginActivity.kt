package com.example.firebaseproduct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseproduct.R
import com.example.firebaseproduct.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : BaseActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            intent(this, RegisterActivity())
        }
        binding.loginButton.click {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (validate(email, password, binding.emailInput, binding.passwordInput)) {
                binding.loadingProgressBar.isIndeterminate = true
                login(email, password)
            }
        }
    }
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.loadingProgressBar.isIndeterminate = false
                    toast("Log in success")
                    intent(this, MainActivity())
                    finish()
                } else {
                    toast("User does not exits!")
                    binding.loadingProgressBar.isIndeterminate = false
                }
            }
    }
}