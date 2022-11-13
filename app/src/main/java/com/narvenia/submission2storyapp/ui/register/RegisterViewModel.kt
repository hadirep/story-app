package com.narvenia.submission2storyapp.ui.register

import androidx.lifecycle.ViewModel
import com.narvenia.submission2storyapp.data.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = storyRepository.register(name, email, password)
}