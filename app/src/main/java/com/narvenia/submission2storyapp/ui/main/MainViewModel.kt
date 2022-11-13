package com.narvenia.submission2storyapp.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.narvenia.submission2storyapp.data.StoryRepository
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem

class MainViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun getAllStory(token: String) : LiveData<PagingData<ListStoryItem>>
    = storyRepository.getAllStory(token).cachedIn(viewModelScope)
}