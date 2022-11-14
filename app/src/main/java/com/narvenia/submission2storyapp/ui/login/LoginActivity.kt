package com.narvenia.submission2storyapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.narvenia.submission2storyapp.R
import com.narvenia.submission2storyapp.data.local.session.UserSession
import com.narvenia.submission2storyapp.ui.main.MainActivity
import com.narvenia.submission2storyapp.data.remote.response.LoginResult
import com.narvenia.submission2storyapp.databinding.ActivityLoginBinding
import com.narvenia.submission2storyapp.utils.ViewModelFactory
import com.narvenia.submission2storyapp.ui.register.RegisterActivity
import com.narvenia.submission2storyapp.data.Result

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding1 get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels {
            factory
        }

        binding.progressBar.visibility = View.GONE

        val pref = UserSession(this)
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                ).observe(this@LoginActivity) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                pref.getUser(
                                    LoginResult(
                                        name = it.data?.name,
                                        token = it.data?.token,
                                        isLogin = true
                                    )
                                )
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this@LoginActivity,
                                    resources.getString(R.string.login_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@LoginActivity,
                                    resources.getString(R.string.login_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            tvLocalization.setOnClickListener{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            goToRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
