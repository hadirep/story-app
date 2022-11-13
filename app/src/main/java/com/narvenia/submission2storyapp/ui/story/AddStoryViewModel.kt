package com.narvenia.submission2storyapp.ui.story

import androidx.lifecycle.ViewModel
import com.narvenia.submission2storyapp.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun addNewStory(token: String, image: MultipartBody.Part, description: RequestBody)
    = storyRepository.addNewStory(token, image, description)
}