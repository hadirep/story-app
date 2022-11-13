package com.narvenia.submission2storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.narvenia.submission2storyapp.R
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem
import com.narvenia.submission2storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = resources.getString(R.string.detail_app_name)

        val storyItem: ListStoryItem? = intent.getParcelableExtra(EXTRA_DETAIL)
        binding.apply{
            if (storyItem != null) {
                Glide.with(this@DetailActivity)
                    .load(storyItem.photoUrl)
                    .circleCrop()
                    .into(binding.ivAvatar)
                tvName.text = storyItem.name
                tvDescription.text = storyItem.description
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}