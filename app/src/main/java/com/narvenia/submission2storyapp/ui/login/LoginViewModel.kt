package com.narvenia.submission2storyapp.ui.login

import androidx.lifecycle.*
import com.narvenia.submission2storyapp.data.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) = storyRepository.login(email, password)
}