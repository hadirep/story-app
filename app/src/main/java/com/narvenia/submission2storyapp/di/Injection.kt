package com.narvenia.submission2storyapp.di

import android.content.Context
import com.narvenia.submission2storyapp.data.StoryRepository
import com.narvenia.submission2storyapp.data.local.database.StoryDatabase
import com.narvenia.submission2storyapp.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getInstance(context)
        return StoryRepository.getInstance(apiService, database)
    }
}