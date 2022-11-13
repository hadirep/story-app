package com.narvenia.submission2storyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.narvenia.submission2storyapp.R
import com.narvenia.submission2storyapp.adapter.LoadingStateAdapter
import com.narvenia.submission2storyapp.adapter.StoryAdapter
import com.narvenia.submission2storyapp.data.local.session.UserSession
import com.narvenia.submission2storyapp.data.remote.response.LoginResult
import com.narvenia.submission2storyapp.databinding.ActivityMainBinding
import com.narvenia.submission2storyapp.ui.login.LoginActivity
import com.narvenia.submission2storyapp.ui.maps.MapsActivity
import com.narvenia.submission2storyapp.ui.story.AddStoryActivity
import com.narvenia.submission2storyapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = resources.getString(R.string.list_app_name)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: MainViewModel by viewModels {
            factory
        }

        pref = UserSession(this)
        val user: LoginResult = pref.getToken()

        val storyAdapter = StoryAdapter()
        binding.apply{
            rvUser.adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)

            fabAdd.setOnClickListener {
                val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.getAllStory("Bearer ${user.token}").observe(this) {
           storyAdapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.localization -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                pref.getUser(
                    LoginResult(
                        name = null,
                        token = null,
                        isLogin = false
                    )
                )
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, resources.getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}