package com.narvenia.submission2storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.narvenia.submission2storyapp.data.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getMaps(token: String) =  storyRepository.getMaps(token)
}